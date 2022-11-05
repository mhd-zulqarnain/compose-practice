package com.project.tailor.data.login

import android.content.Context
import com.project.tailor.R
import com.project.tailor.api.ApiParams
import com.project.tailor.api.AuthApi
import com.project.tailor.api.Result
import com.project.tailor.model.LoggedInUser
import com.project.tailor.network.NetworkHandler
import com.project.tailor.utils.mapToRequestBody
import com.project.tailor.utils.processResponse
import com.project.tailor.utils.safeApiCall
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.RequestBody
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginDataSource @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val networkHandler: NetworkHandler,
    private val authApi: AuthApi
) {


    suspend fun login(email: String, password: String): Result<LoggedInUser> {
        return when (networkHandler.isConnected) {
            true -> {
                safeApiCall(
                    call = {
                        callLoginApi(
                            ApiParams.loginParams(
                                email, password, "drRJL80qtyupT8So68m1DnJHuVSVt7WKR4hIHHAw"
                            )
                        )
                    },
                    errorMessage = context.getString(R.string.error_msg)
                )
            }
            false -> {
                Result.Error(IOException(context.getString(R.string.failure_network_connection)))
            }
        }
    }

    private suspend fun callLoginApi(
        params: HashMap<String, Any>
    ): Result<LoggedInUser> {
        val body: RequestBody = params.mapToRequestBody()
        return authApi.loginCall(body).processResponse()
    }

    suspend fun facebookLogin(token: String): Result<Any> {
        return when (networkHandler.isConnected) {
            true -> {
                safeApiCall(
                    call = {
                        facebookLoginApi(
                            ApiParams.socialParams(
                                token
                            )
                        )
                    },
                    errorMessage = context.getString(R.string.error_msg)
                )
            }
            false -> {
                Result.Error(IOException(context.getString(R.string.failure_network_connection)))
            }
        }
    }

    private suspend fun facebookLoginApi(
        params: HashMap<String, Any>
    ): Result<Any> {
        val body: RequestBody = params.mapToRequestBody()
        return authApi.facebookLoginCall(body).processResponse()
    }

    suspend fun googleLogin(token: String): Result<Any> {
        return when (networkHandler.isConnected) {
            true -> {
                safeApiCall(
                    call = {
                        googleLoginApi(
                            ApiParams.socialParams(
                                token
                            )
                        )
                    },
                    errorMessage = context.getString(R.string.error_msg)
                )
            }
            false -> {
                Result.Error(IOException(context.getString(R.string.failure_network_connection)))
            }
        }
    }

    private suspend fun googleLoginApi(
        params: HashMap<String, Any>
    ): Result<Any> {
        val body: RequestBody = params.mapToRequestBody()
        return authApi.googleLoginCall(body).processResponse()
    }

}