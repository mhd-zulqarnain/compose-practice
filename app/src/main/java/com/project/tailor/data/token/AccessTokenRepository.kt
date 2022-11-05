package com.project.tailor.data.token

import com.project.tailor.data.login.LoginDataSource
import com.project.tailor.data.storage.PersistenceStorage
import com.project.tailor.di.qualifiers.EncryptedStorage
import com.project.tailor.model.AccessToken
import com.project.tailor.utils.KEY_ACCESS_TOKEN
import com.google.gson.Gson
import com.project.tailor.storage.Storage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenRepository @Inject constructor(
    @EncryptedStorage
    private val encryptedStorage: Storage,
    val gson: Gson
) {

    fun getAccessToken(): AccessToken? {
        return try {
            gson.fromJson(
                encryptedStorage.getString(KEY_ACCESS_TOKEN), AccessToken::class.java
            )
        } catch (e: Exception) {
            null
        }
    }

    fun saveAccessToken(
        accessToken: AccessToken?
    ) {
        accessToken?.let {
            encryptedStorage.setString(
                KEY_ACCESS_TOKEN,
                gson.toJson(
                    accessToken
                )
            )
        }
    }

}