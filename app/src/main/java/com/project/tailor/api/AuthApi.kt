package com.project.tailor.api

import com.project.tailor.model.LoggedInUser
import com.project.tailor.model.SignUpResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

const val URL_LOGIN = "api/auth/login"
const val URL_SIGNUP = "api/auth/register"
const val URL_FACEBOOK = "api/auth/facebook-login"
const val URL_GOOGLE = "api/auth/google-login"


interface AuthApi {

    @POST(URL_LOGIN)
    suspend fun loginCall(
        @Body params: RequestBody
    ): Response<LoggedInUser>

    @POST(URL_SIGNUP)
    suspend fun signUpCall(
        @Body params: RequestBody
    ): Response<SignUpResponse>

    @POST(URL_GOOGLE)
    suspend fun googleLoginCall(
        @Body params: RequestBody
    ): Response<Any>

    @POST(URL_FACEBOOK)
    suspend fun facebookLoginCall(
        @Body params: RequestBody
    ): Response<Any>

}
