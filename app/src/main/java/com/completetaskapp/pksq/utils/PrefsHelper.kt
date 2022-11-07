package com.completetaskapp.pksq.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import io.michaelrocks.paranoid.Obfuscate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Obfuscate
class PrefsHelper @Inject constructor(private val context: Context) {

    private object PreferencesKeys {
        const val APP_SHARED_PREFERENCES = "shared_preferences"
        const val IS_NON_ORGANIC = "hash_status"
        const val IS_HASH_READY = "is_hash_ready"
        const val LAST_PAGE = "last_page"
    }

    private val sharedPreferences by lazy {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            PreferencesKeys.APP_SHARED_PREFERENCES,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    val isHashReady: Boolean get() = sharedPreferences.getBoolean(PreferencesKeys.IS_HASH_READY, false)
    val isNonOrganic: Boolean get() = sharedPreferences.getBoolean(PreferencesKeys.IS_NON_ORGANIC, false)
    val lastPage: String get() = sharedPreferences.getString(PreferencesKeys.LAST_PAGE, "") ?: ""

    fun getParam(param: String): String {
        return sharedPreferences.getString(param, "") ?: ""
    }

    fun setParam(param: String?, value: String) {
        sharedPreferences.edit().putString(param, value).apply()
    }

    fun setHashStatus(isHashReady: Boolean) {
        sharedPreferences.edit().putBoolean(PreferencesKeys.IS_HASH_READY, isHashReady).apply()
    }

    fun setNonOrganic(isNonOrganic: Boolean) {
        sharedPreferences.edit().putBoolean(PreferencesKeys.IS_NON_ORGANIC, isNonOrganic).apply()
    }
}