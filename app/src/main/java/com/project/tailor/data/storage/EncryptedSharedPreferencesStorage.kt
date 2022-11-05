package com.project.tailor.data.storage

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.project.tailor.R
import com.project.tailor.storage.Storage
import java.io.File
import java.security.GeneralSecurityException
import java.security.KeyStore

private const val KEYSTORE_PROVIDER = "AndroidKeyStore"

class EncryptedSharedPreferencesStorage(var context: Context) : Storage {
    private val preferenceName = context.getString(R.string.app_name) + "-encrypted"

    // EncryptedSharedPreferences to save data .more securely
    private val sharedPreferences: SharedPreferences
    lateinit var masterKeyAlias: MasterKey

    init {
        sharedPreferences = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            masterKeyAlias = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            // EncryptedSharedPreferences to save data more securely
            try {
                createSharedPreferences()
            } catch (gsException: GeneralSecurityException) {
                // There's not much point in keeping data you can't decrypt anymore,
                // delete & re-create; user has to start from scratch
                deleteSharedPreferences()
                createSharedPreferences()
            }
        } else {
            //As the security API is not available before Android M we use standard shared preferences
            context.getSharedPreferences(
                preferenceName,
                Context.MODE_PRIVATE
            )
        }
    }

    private fun createSharedPreferences() = EncryptedSharedPreferences.create(
        context,
        context.getString(R.string.app_name) + "-encrypted",
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // Clearing getSharedPreferences using default Preference wrapper.
    // This is to work around any key-mismatches that may happen.
    private fun clearSharedPreference() {
        context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE).edit().clear()
            .apply()
    }

    // Workaround [https://github.com/google/tink/issues/535#issuecomment-912170221]
    // Issue Tracker - https://issuetracker.google.com/issues/176215143?pli=1
    private fun deleteSharedPreferences() {
        try {
            val sharedPrefsFile =
                File("${context.filesDir.parent}/shared_prefs/$preferenceName.xml")

            // Clear the encrypted prefs
            clearSharedPreference()

            // Delete the encrypted prefs file
            if (sharedPrefsFile.exists()) {
                val deleted = sharedPrefsFile.delete()
            }

            // Delete the master key
            val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER)
            keyStore.load(null)
            keyStore.deleteEntry(MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue)!!
    }

    override fun remove(key: String) {
        sharedPreferences.edit {
            remove(key)
        }
    }

    override fun setBoolean(key: String, value: Boolean) = sharedPreferences.edit {
        putBoolean(key, value)
    }

    override fun setInt(key: String, value: Int) = sharedPreferences.edit {
        putInt(key, value)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        sharedPreferences.getBoolean(key, defaultValue)

    override fun getInt(key: String, defaultValue: Int): Int =
        sharedPreferences.getInt(key, defaultValue)

}