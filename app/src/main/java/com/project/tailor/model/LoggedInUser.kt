package com.project.tailor.model

import com.google.gson.annotations.SerializedName

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    @SerializedName("success" ) var success : Boolean? = null,
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("data"    ) var data    : Data?    = Data(),
    @SerializedName("token"   ) var token   : String?  = null
)
data class Data (

    @SerializedName("id"                ) var id              : Int?    = null,
    @SerializedName("name"              ) var name            : String? = null,
    @SerializedName("email"             ) var email           : String? = null,
    @SerializedName("email_verified_at" ) var emailVerifiedAt : String? = null,
    @SerializedName("guid"              ) var guid            : String? = null,
    @SerializedName("remember_token"    ) var rememberToken   : String? = null,
    @SerializedName("created_at"        ) var createdAt       : String? = null,
    @SerializedName("updated_at"        ) var updatedAt       : String? = null,
    @SerializedName("profile_url"       ) var profileUrl      : String? = null,
    @SerializedName("phone"             ) var phone           : String? = null,
    @SerializedName("location"          ) var location        : String? = null

)