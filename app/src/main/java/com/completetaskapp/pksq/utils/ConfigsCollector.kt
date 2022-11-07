package com.completetaskapp.pksq.utils

import android.content.Context
import com.completetaskapp.pksq.data.models.ConfigSettings
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.gson.Gson
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
class ConfigsCollector(
    context: Context,
    prefsHelper: PrefsHelper,
    onCollectorResponse: OnCollectorResponse
) {

    init {

        val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
        firebaseAnalytics.setUserProperty("non_organic", prefsHelper.isNonOrganic.toString())

        val mFirebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(300)
            .build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        mFirebaseRemoteConfig.fetchAndActivate()
            .addOnSuccessListener {
                val settings: ConfigSettings? = Gson().fromJson(
                    mFirebaseRemoteConfig.getString("settings"),
                    ConfigSettings::class.java
                )

                onCollectorResponse.onResponse(settings)
            }
            .addOnFailureListener {
                onCollectorResponse.onResponse(ConfigSettings())
            }
            .addOnCanceledListener {
                onCollectorResponse.onResponse(ConfigSettings())
            }
    }

    fun interface OnCollectorResponse {
        fun onResponse(configSettings: ConfigSettings?)
    }
}