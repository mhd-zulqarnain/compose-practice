package com.project.tailor.model

import com.google.gson.annotations.SerializedName

class SignUpResponse (
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("errors"  ) var errors  : Errors? = Errors()
)

data class Errors (

    @SerializedName("email" ) var email : ArrayList<String> = arrayListOf()

)