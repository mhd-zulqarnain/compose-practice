package com.project.tailor.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class AccessToken(
    @SerializedName("token")
    val token: String
) : Parcelable {

}