package com.completetaskapp.pksq.data.repository

import android.content.Context
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.completetaskapp.pksq.data.models.ConfigSettings
import com.completetaskapp.pksq.data.models.UserDeeplinks
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.ads.identifier.AdvertisingIdClient.getAdvertisingIdInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.facebook.FacebookSdk
import com.onesignal.OneSignal
import com.completetaskapp.pksq.BuildConfig
import com.completetaskapp.pksq.utils.*
import io.michaelrocks.paranoid.Obfuscate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Obfuscate
@ExperimentalCoroutinesApi
class Repository(
    private val context: Context,
    private val prefsHelper: PrefsHelper
) {

    private fun String.urlEncodeString(): String = URLEncoder.encode(
        this,
        StandardCharsets.UTF_8.displayName()
    )

    private fun manageAdCampaign(campaign: String?) {

        campaign?.let { c ->

            val campaignParts = c
                .split("_")
                .toTypedArray()

            if (campaignParts.isNotEmpty()) {
                if (campaignParts[0].equals("app", true)) {

                    for (i in campaignParts.indices) {

                        prefsHelper.setParam(
                            param = "android_app_sub_${i + 1}",
                            value = campaignParts[i]
                        )
                    }

                    prefsHelper.setNonOrganic(true)
                }
            }

            prefsHelper.setParam("campaign", c)
        }
    }

    private val facebookDeferredAppLink: Flow<Unit> = callbackFlow {

        FacebookSdk.sdkInitialize(
            context
        ) {

            AppLinkData.fetchDeferredAppLinkData(
                context
            ) { appLinkData ->

                appLinkData?.targetUri?.let { uri ->
                    manageAdCampaign(campaign = uri.getQueryParameter("campaign"))
                }

                trySend(Unit)
            }
        }

        awaitClose()

    }.flowOn(Dispatchers.IO)

    private val advertisingId: Flow<String?> = flow {

        val googleAdvertisingId = prefsHelper.getParam("advertising_id")

        if (googleAdvertisingId.isNotEmpty()) {
            emit(googleAdvertisingId)
        } else {
            runCatching {
                val advertisingId: String?
                val idInfo: AdvertisingIdClient.Info = getAdvertisingIdInfo(context)
                advertisingId = idInfo.id
                advertisingId?.let {
                    prefsHelper.setParam("advertising_id", advertisingId)
                }
                emit(advertisingId)
            }.onFailure { e ->
                val errorString = "not_collected: Failure ${e.message}"
                prefsHelper.setParam("advertising_id", errorString)
                emit(errorString)
            }
        }

    }.flowOn(Dispatchers.IO)

    private val installReferrer: Flow<String> = callbackFlow {

        InstallReferrerCollector(
            context = context,
            onReferrerSetupFinished = { response ->
                prefsHelper.setParam("google_install_referrer", response)
                trySend(response)
            }
        )

        awaitClose()
    }

    private val appsflyerAttribution: Flow<Unit> = callbackFlow {

        val appsflyer = AppsFlyerLib.getInstance()

        val conversionDataListener: AppsFlyerConversionListener = object : AppsFlyerConversionListener {

            override fun onConversionDataSuccess(conversionData: MutableMap<String, Any>) {

                //Log.e("Appsflyer", "onConversionDataSuccess: $conversionData")

                val gson = Gson()
                val userDeeplinks: UserDeeplinks = Gson().fromJson(
                    gson.toJson(conversionData),
                    object : TypeToken<UserDeeplinks>() {}.type
                )

                userDeeplinks.apply {

                    manageAdCampaign(campaign = campaign)

                    prefsHelper.setParam("af_status", af_status)

                    media_source?.let { media_source ->
                        prefsHelper.setParam("media_source", media_source)
                    }
                    agency?.let { agency ->
                        prefsHelper.setParam("media_source", agency)
                    }
                    ad_id?.let { ad_id ->
                        prefsHelper.setParam("ad_id", ad_id)
                    }
                    adset_id?.let { adgroup_id ->
                        prefsHelper.setParam("adset_id", adgroup_id)
                    }
                    adgroup?.let { adgroup_name ->
                        prefsHelper.setParam("adgroup", adgroup_name)
                    }
                    adset?.let { ad_objective_name ->
                        prefsHelper.setParam("adset", ad_objective_name)
                    }
                    campaign_id?.let { campaign_id ->
                        prefsHelper.setParam("campaign_id", campaign_id)
                    }
                }

                if (!prefsHelper.isHashReady) {

                    val appsflyerUid = appsflyer.getAppsFlyerUID(context)

                    appsflyerUid?.let {
                        prefsHelper.setParam("hash", it)
                        prefsHelper.setHashStatus(true)
                    }
                }

                trySend(Unit)
                appsflyer.unregisterConversionListener()
            }

            override fun onConversionDataFail(errorMessage: String) {
                prefsHelper.setNonOrganic(false)
                trySend(Unit)
                appsflyer.unregisterConversionListener()
                Log.e("Appsflyer", errorMessage)
            }

            override fun onAppOpenAttribution(conversionData: MutableMap<String, String>) {
                for (attrName in conversionData.keys) {
                    Log.e("Log", "attribute: $attrName =  ${conversionData[attrName]}")
                }
            }

            override fun onAttributionFailure(errorMessage: String) {}
        }
        appsflyer.init(Constants.APPSFLYER_DEV_KEY, conversionDataListener, context)
        appsflyer.start(context, Constants.APPSFLYER_DEV_KEY)
        if (BuildConfig.DEBUG) appsflyer.setDebugLog(true)

        awaitClose()
    }

    val configSettings: Flow<Resource<ConfigSettings?>> = callbackFlow {

        launch { advertisingId.collect() }
        launch { installReferrer.collect() }

        facebookDeferredAppLink.collect {

            appsflyerAttribution.collect {

                ConfigsCollector(
                    context = context,
                    prefsHelper = prefsHelper,
                    onCollectorResponse = { configSettings ->

                        //Log.e("ConfigsCollector", "configSettings: $configSettings")

                        configSettings?.path?.let { path ->
                            OneSignal.provideUserConsent(true)
                            prefsHelper.apply {
                                setParam("path", path)
                            }
                        }
                        trySend(Resource.success(configSettings))
                    }
                )
            }
        }
        
        awaitClose()

    }.flowOn(Dispatchers.IO)

    val link: Flow<String> = flow {

        var i = 1

        prefsHelper.apply {

            val link = lastPage.ifEmpty {
                buildString {
                    append(getParam("path"))
                    append("?media_source=${getParam("media_source")}")
                    append("&hash=${getParam("hash").urlEncodeString()}")
                    append("&advertising_id=${getParam("advertising_id").urlEncodeString()}")
                    append("&google_install_referrer=${getParam("google_install_referrer").urlEncodeString()}")
                    append("&ad_id=${getParam("ad_id").urlEncodeString()}")
                    append("&adset_id=${getParam("adset_id").urlEncodeString()}")
                    append("&adgroup=${getParam("adgroup").urlEncodeString()}")

                    do {
                        if (i == 1)
                            append("&android_app_sub_$i=${context.packageName.urlEncodeString()}")
                        else
                            append("&android_app_sub_$i=${getParam("android_app_sub_$i").urlEncodeString()}")

                        i++
                    } while (getParam("android_app_sub_$i").isNotEmpty())
                }
            }

            emit(link)
        }

    }.flowOn(Dispatchers.IO)

    data class Resource<out T>(
        val status: Status,
        val data: T?,
        val message: String?
    ) {
        companion object {
            fun <T> success(data: T): Resource<T> = Resource(
                status = Status.SUCCESS,
                data = data,
                message = null
            )

            fun <T> error(data: T?, message: String?): Resource<T> = Resource(
                status = Status.ERROR,
                data = data,
                message = message
            )

            fun <T> loading(data: T?): Resource<T> = Resource(
                status = Status.LOADING,
                data = data,
                message = null
            )
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
    }
}