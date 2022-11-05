package com.project.tailor.data.userdetails

import android.content.Context
import com.project.tailor.R
import com.project.tailor.api.FlexApi
import com.project.tailor.api.Result
import com.project.tailor.model.UserDetails
import com.project.tailor.network.NetworkHandler
import com.project.tailor.utils.processResponse
import com.project.tailor.utils.safeApiCall
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDetailsDataSource @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val networkHandler: NetworkHandler,
    private val flexApi: FlexApi
) {

    suspend fun getUserDetails(): Result<UserDetails> {
        return when (networkHandler.isConnected) {
            true -> {
                safeApiCall(
                    call = {
                        callUserDetailsApi(
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

    private suspend fun callUserDetailsApi(
    ): Result<UserDetails> {
        return flexApi.getUserDetails().processResponse()
    }

}