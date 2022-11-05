package com.project.tailor.api

import android.content.Context
import android.provider.Telephony.Carriers.BEARER
import com.project.tailor.data.token.AccessTokenRepository
import com.google.common.net.HttpHeaders.AUTHORIZATION
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class responsible to add AccessToken to each call
 */
@Singleton
class AccessTokenInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val accessTokenRepository: AccessTokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = accessTokenRepository.getAccessToken()
        return if (accessToken != null) {
            val requestBuilder = buildRequestHeadersForUserInfo(
                originalRequest,
                accessToken.token
            )
            val request = requestBuilder.build()
            chain.proceed(request)
        } else chain.proceed(originalRequest)
    }


    /**
     * add authorization Bearer in header
     * to hit end point specific to fusion Auth user info
     * **/
    private fun buildRequestHeadersForUserInfo(
        originalRequest: Request,
        accessToken: String
    ): Request.Builder {
        val requestBuilder = originalRequest.newBuilder()
        requestBuilder.addHeader(
            AUTHORIZATION, "$BEARER $accessToken"
        )
        return requestBuilder
    }

}