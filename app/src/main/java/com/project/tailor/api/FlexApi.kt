package com.project.tailor.api

import com.project.tailor.model.UserDetails
import retrofit2.Response
import retrofit2.http.GET


const val BASE_URL = "http://back.letsdeploy.us"
const val URL_USER_DETAILS = "api/user/detail"

interface FlexApi {
    @GET(URL_USER_DETAILS)
    suspend fun getUserDetails(
    ): Response<UserDetails>


}
