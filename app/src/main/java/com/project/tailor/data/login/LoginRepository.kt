package com.project.tailor.data.login

import com.project.tailor.api.Result
import com.project.tailor.data.storage.PersistenceStorage
import com.project.tailor.model.LoggedInUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val dataSource: LoginDataSource,
    private val persistenceStorage: PersistenceStorage
) {

     fun login(email: String, password: String): Flow<Result<String>> = flow {
        try {
            when (val result = dataSource.login(email, password)) {
                is Result.Success -> {
                    val data = result.data
                    setLoggedInUser(data)
                    emit(Result.Success("data"))
                }
                is Result.Error -> {
                    emit(Result.Error(Exception("failed to logged in")))
                }is Result.Loading -> {
                    emit(Result.Loading)
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


    fun facebookLogin(token: String): Flow<Result<String>> = flow {
        try {
            when (val result = dataSource.facebookLogin(token)) {
                is Result.Success -> {
                    val data = result.data
                    emit(Result.Success("data"))
                }
                is Result.Error -> {
                    emit(Result.Error(Exception("failed to logged in")))
                    emit(result)
                }
                is Result.Loading -> {
                    emit(Result.Loading)
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    fun googleLogin(token: String): Flow<Result<String>> = flow {
        try {
            when (val result = dataSource.googleLogin(token)) {
                is Result.Success -> {
                    val data = result.data
                    emit(Result.Success("data"))
                }
                is Result.Error -> {
                    emit(Result.Error(Exception("failed to logged in")))
                    emit(result)
                }
                is Result.Loading -> {
                    emit(Result.Loading)
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}