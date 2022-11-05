package com.project.tailor.api

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

const val SUCCESS_CODE = 200

class MockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        if (BuildConfig.FLAVOR == "dev") {
        if (true) {
            val uri = chain.request().url.toUri().toString()
            val responseString = when {
             uri.contains(URL_LOGIN) -> loginSuccess
             uri.contains(URL_SIGNUP) -> signUpSuccess
                 else -> ""
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(SUCCESS_CODE)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with devStaging mode"
            )
        }
    }
}


const val loginSuccess = """
{
    "success": true,
    "message": "Successful login",
    "data": {
        "id": 7,
        "name": "ecom",
        "email": "ecom@ecom.com",
        "email_verified_at": "2022-03-13 16:21:17",
        "guid": "9de580e7-7b9d-43fc-a63d-ca8563921ce3",
        "remember_token": null,
        "created_at": "2022-03-13T16:20:42.000000Z",
        "updated_at": "2022-03-13T17:15:37.000000Z",
        "profile_url": "https://api.flexemarket.com/storage/users/7/User/3cabcc78-d48f-4f4e-9a99-11b5dda9f590.jpg",
        "phone": "+124312313123",
        "location": null
    },
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiYTk2Y2UxY2IxZmFhNWE2YzIwNzBmYjEyNzY0ZjRhNWU3YjQ1ZTdmNGE0NzRhNTNmMmNlY2ZlZDRhMDcxMzU1ZTNkZjQ1NmFiYzIyYjU5MzMiLCJpYXQiOjE2NTExNzM0MjAsIm5iZiI6MTY1MTE3MzQyMCwiZXhwIjoxNjgyNzA5NDIwLCJzdWIiOiI3Iiwic2NvcGVzIjpbXX0.ing85NKufpHsJmYSwpNuw2urGKhsK8nq0oMIlryY-nKAHa51_0VmRcF6mLLKoQokSektYF2-NweEO_BDFP3wDjAhgc-B0fAFD62t33S5EyasBMFmrjuO9G1gTAdPYcEkQAGKfA-YlhonfBUratfpb8bBbXfTCoDEh-SrmLJ0QTHNg23ZRUHzKMmt8Sv8b_zVCWuEnldDvU0wr_Pj5m27ZBvOCDnHWV9rn74IdEA113b3fMMKCziy9ZEThgJ22_i5_1jSMeWOm8xUh5Bj_MSOwhasemI-uZ2trwX8jj0uYrjwCf-oshda-NUgGjo3aYgo0F-90MEiN1Ztd3Xw70vd6LIPzL_oDd2pAaGh9226Q0seJvO0RC3SfYFvLbXMkKN0OauwYMlM-cAdk7gPFPI25VD1yHYFFDNTTZ38eLk-7SmCY-gQXx3PXsRgEMgkhA1QrGpJl_ljuCko4w8GXNuoVQDzxc1Z58cV-rLW1wlF8bNG9IoQXaWlgBeaqfh_k-Oi943BLkhF_7Gom1U5UwWzQxJBcSiVFw9AQZ9L_PtjJzXpMRhfsyd2RzfxkyBYd_z7sAJTT8z0-R44YQXVWBdpDatso41elZ2TMftYAF7wB3v0iha5ILkKknx9DiXKFqWUFCj0RBpkAsUuvr0EhnbQU0kmEwcAjYxq6Nqy6cQzP4I"
}
"""

const val signUpSuccess = """
{
    "success": true,
    "message": "Please verify your email"
}
"""

const val signUpSuccessAlreadyExists = """
{
    "message": "The given data was invalid.",
    "errors": {
        "email": [
            "The email has already been taken."
        ]
    }
}
"""
