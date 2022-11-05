package com.project.tailor.data.storage

import com.project.tailor.model.LoggedInUser
import com.project.tailor.model.UserDetails

interface PersistenceStorage {
    fun setLoggedInUser(loggedInUser: LoggedInUser?)
    fun getLoggedInUser(): LoggedInUser?

    fun setUserDetails(userDetails: UserDetails?)
    fun getUserDetails(): UserDetails?

}