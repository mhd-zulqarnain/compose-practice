package com.project.tailor.api

import com.google.gson.annotations.SerializedName

class Envelope<T>(
    @SerializedName("header")
    val header: Header,
    @SerializedName("body")
    val body: T
)

class Header(
    @SerializedName("message")
    val message: String,
    @SerializedName("response_code")
    val code: Int = 0
)


