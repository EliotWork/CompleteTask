package com.completetaskapp.pksq.utils

import android.content.Context
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
class InstallReferrerCollector(
    context: Context,
    private val onReferrerSetupFinished: OnReferrerSetupFinished
) {

    init {
        val referrerClient = InstallReferrerClient.newBuilder(context).build()
        referrerClient.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                when (responseCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        runCatching {
                            referrerClient.installReferrer?.let { referrerDetails ->
                                onReferrerSetupFinished.onReferrerReceived(
                                    referrerDetails.installReferrer
                                )
                            }
                        }.onFailure { e ->
                            e.printStackTrace()
                            onReferrerSetupFinished.onReferrerReceived(
                                e.message ?: "REFERRER_READ_FAILURE"
                            )
                        }
                    }
                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED ->
                        onReferrerSetupFinished.onReferrerReceived("FEATURE_NOT_SUPPORTED")
                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE ->
                        onReferrerSetupFinished.onReferrerReceived("SERVICE_UNAVAILABLE")
                    InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR ->
                        onReferrerSetupFinished.onReferrerReceived("DEVELOPER_ERROR")
                    InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED ->
                        onReferrerSetupFinished.onReferrerReceived("SERVICE_DISCONNECTED")
                    InstallReferrerClient.InstallReferrerResponse.PERMISSION_ERROR ->
                        onReferrerSetupFinished.onReferrerReceived("PERMISSION_ERROR")
                }

            }

            override fun onInstallReferrerServiceDisconnected() {
                onReferrerSetupFinished.onReferrerReceived("SERVICE_DISCONNECTED")
            }
        })
    }

    fun interface OnReferrerSetupFinished {
        fun onReferrerReceived(response: String)
    }
}

