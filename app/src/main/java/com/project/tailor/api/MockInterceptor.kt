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
             uri.contains(URL_PRODUCTS) -> signUpSuccessAlreadyExists
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
const val products = """
{
  "products": [
    {
      "id": 1,
      "title": "iPhone 9",
      "description": "An apple mobile which is nothing like apple",
      "price": 549,
      "discountPercentage": 12.96,
      "rating": 4.69,
      "stock": 94,
      "brand": "Apple",
      "category": "smartphones",
      "thumbnail": "https://dummyjson.com/image/i/products/1/thumbnail.jpg",
      "images": [
        "https://dummyjson.com/image/i/products/1/1.jpg",
        "https://dummyjson.com/image/i/products/1/2.jpg",
        "https://dummyjson.com/image/i/products/1/3.jpg",
        "https://dummyjson.com/image/i/products/1/4.jpg",
        "https://dummyjson.com/image/i/products/1/thumbnail.jpg"
      ]
    },
    {
      "id": 2,
      "title": "iPhone X",
      "description": "SIM-Free, Model A19211 6.5-inch Super Retina HD display with OLED technology A12 Bionic chip with ...",
      "price": 899,
      "discountPercentage": 17.94,
      "rating": 4.44,
      "stock": 34,
      "brand": "Apple",
      "category": "smartphones",
      "thumbnail": "https://dummyjson.com/image/i/products/2/thumbnail.jpg",
      "images": [
        "https://dummyjson.com/image/i/products/2/1.jpg",
        "https://dummyjson.com/image/i/products/2/2.jpg",
        "https://dummyjson.com/image/i/products/2/3.jpg",
        "https://dummyjson.com/image/i/products/2/thumbnail.jpg"
      ]
    },
    {
      "id": 3,
      "title": "Samsung Universe 9",
      "description": "Samsung's new variant which goes beyond Galaxy to the Universe",
      "price": 1249,
      "discountPercentage": 15.46,
      "rating": 4.09,
      "stock": 36,
      "brand": "Samsung",
      "category": "smartphones",
      "thumbnail": "https://dummyjson.com/image/i/products/3/thumbnail.jpg",
      "images": [
        "https://dummyjson.com/image/i/products/3/1.jpg"
      ]
    },
    {
      "id": 4,
      "title": "OPPOF19",
      "description": "OPPO F19 is officially announced on April 2021.",
      "price": 280,
      "discountPercentage": 17.91,
      "rating": 4.3,
      "stock": 123,
      "brand": "OPPO",
      "category": "smartphones",
      "thumbnail": "https://dummyjson.com/image/i/products/4/thumbnail.jpg",
      "images": [
        "https://dummyjson.com/image/i/products/4/1.jpg",
        "https://dummyjson.com/image/i/products/4/2.jpg",
        "https://dummyjson.com/image/i/products/4/3.jpg",
        "https://dummyjson.com/image/i/products/4/4.jpg",
        "https://dummyjson.com/image/i/products/4/thumbnail.jpg"
      ]
    },
    {
      "id": 5,
      "title": "Huawei P30",
      "description": "Huawei’s re-badged P30 Pro New Edition was officially unveiled yesterday in Germany and now the device has made its way to the UK.",
      "price": 499,
      "discountPercentage": 10.58,
      "rating": 4.09,
      "stock": 32,
      "brand": "Huawei",
      "category": "smartphones",
      "thumbnail": "https://dummyjson.com/image/i/products/5/thumbnail.jpg",
      "images": [
        "https://dummyjson.com/image/i/products/5/1.jpg",
        "https://dummyjson.com/image/i/products/5/2.jpg",
        "https://dummyjson.com/image/i/products/5/3.jpg"
      ]
    },
    {
      "id": 6,
      "title": "MacBook Pro",
      "description": "MacBook Pro 2021 with mini-LED display may launch between September, November",
      "price": 1749,
      "discountPercentage": 11.02,
      "rating": 4.57,
      "stock": 83,
      "brand": "APPle",
      "category": "laptops",
      "thumbnail": "https://dummyjson.com/image/i/products/6/thumbnail.png",
      "images": [
        "https://dummyjson.com/image/i/products/6/1.png",
        "https://dummyjson.com/image/i/products/6/2.jpg",
        "https://dummyjson.com/image/i/products/6/3.png",
        "https://dummyjson.com/image/i/products/6/4.jpg"
      ]
    },
    {
      "id": 7,
      "title": "Samsung Galaxy Book",
      "description": "Samsung Galaxy Book S (2020) Laptop With Intel Lakefield Chip, 8GB of RAM Launched",
      "price": 1499,
      "discountPercentage": 4.15,
      "rating": 4.25,
      "stock": 50,
      "brand": "Samsung",
      "category": "laptops",
      "thumbnail": "https://dummyjson.com/image/i/products/7/thumbnail.jpg",
      "images": [
        "https://dummyjson.com/image/i/products/7/1.jpg",
        "https://dummyjson.com/image/i/products/7/2.jpg",
        "https://dummyjson.com/image/i/products/7/3.jpg",
        "https://dummyjson.com/image/i/products/7/thumbnail.jpg"
      ]
    }
  ]
}
"""
