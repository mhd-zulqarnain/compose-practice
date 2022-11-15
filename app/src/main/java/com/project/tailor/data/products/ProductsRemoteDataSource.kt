package com.project.tailor.data.products

import android.content.Context
import com.project.tailor.R
import com.project.tailor.api.ApiParams
import com.project.tailor.api.AuthApi
import com.project.tailor.api.FlexApi
import com.project.tailor.api.Result
import com.project.tailor.model.Products
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
class ProductsRemoteDataSource @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val networkHandler: NetworkHandler,
    private val flexApi: FlexApi
) {
    suspend fun getProducts(): Result<Products> {
        return when (networkHandler.isConnected) {
            true -> {
                safeApiCall(
                    call = {
                        callSampleApi()
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
    ): Result<Products> {
        return flexApi.getProducts().processResponse()
    }
}