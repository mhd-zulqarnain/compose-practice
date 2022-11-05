package com.project.tailor.data.userdetails

import com.project.tailor.api.Result
import com.project.tailor.data.storage.PersistenceStorage
import com.project.tailor.model.LoggedInUser
import com.project.tailor.model.UserDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDetailsRepository @Inject constructor(
    private val dataSource: UserDetailsDataSource,
    private val persistenceStorage: PersistenceStorage
) {

    fun getUserDetails(): Flow<Result<UserDetails>> = flow {
        try {
            val details = persistenceStorage.getUserDetails()
            if (details != null) {
                emit(Result.Success(details))
                return@flow
            }
            when (val result = dataSource.getUserDetails()) {
                is Result.Success -> {
                    val data = result.data
                    persistenceStorage.setUserDetails(data)
                    emit(Result.Success(data))
                }
                is Result.Error -> {
                    emit(Result.Error(Exception("failed to get data")))
                    emit(result)
                }
                else -> {
                    //not implemented
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = persistenceStorage.getLoggedInUser()
    }

    fun logout() {
        user = null
        persistenceStorage.setLoggedInUser(null)
    }


    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        persistenceStorage.setLoggedInUser(loggedInUser)
    }

}