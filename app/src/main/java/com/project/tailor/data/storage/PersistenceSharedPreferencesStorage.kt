package com.project.tailor.data.storage

import android.content.Context
import com.project.tailor.di.qualifiers.EncryptedStorage
import com.project.tailor.model.LoggedInUser
import com.project.tailor.model.UserDetails
import com.project.tailor.utils.USER_DETAILS
import com.google.gson.Gson
import com.project.tailor.storage.Storage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val LOGGED_IN_USER = "user"

class PersistenceSharedPreferencesStorage @Inject constructor(
    @ApplicationContext
    val context: Context,
    val gson: Gson,
    @EncryptedStorage
    val storage: Storage
) : PersistenceStorage {


    override fun setLoggedInUser(loggedInUser: LoggedInUser?) {
        val json = if (loggedInUser != null)
            gson.toJson(loggedInUser)
        else
            ""
        storage.setString(LOGGED_IN_USER, json)
    }

    override fun getLoggedInUser(): LoggedInUser? {
        return gson.fromJson(storage.getString(LOGGED_IN_USER), LoggedInUser::class.java)
    }

    override fun setUserDetails(userDetails: UserDetails?) {
        val json = if (userDetails != null)
            gson.toJson(userDetails)
        else
            ""
        storage.setString(USER_DETAILS, json)
    }

    override fun getUserDetails(): UserDetails? {
        return gson.fromJson(storage.getString(USER_DETAILS), UserDetails::class.java)
    }


}