package com.project.tailor.data.signup

import android.content.Context
import com.project.tailor.R
import com.project.tailor.api.ApiParams
import com.project.tailor.api.AuthApi
import com.project.tailor.api.Result
import com.project.tailor.model.LoggedInUser
import com.project.tailor.model.SignUpResponse
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
class SignUpDataSource @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val networkHandler: NetworkHandler,
    private val authApi: AuthApi
) {

    suspend fun signUp(name: String, email: String, password: String): Result<SignUpResponse> {
        return when (networkHandler.isConnected) {
            true -> {
                safeApiCall(
                    call = {
                        callSampleApi(
                            ApiParams.signUpParams(
                                email, password, name
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

    private suspend fun callSampleApi(
        params: HashMap<String, Any>
    ): Result<SignUpResponse> {
        val body: RequestBody = params.mapToRequestBody()
        return authApi.signUpCall(body).processResponse()
    }

}