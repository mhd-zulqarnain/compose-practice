package com.project.tailor.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetails(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("stripe_account_id") var stripeAccountId: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("email_verified_at") var emailVerifiedAt: String? = null,
    @SerializedName("guid") var guid: String? = null,
    @SerializedName("remember_token") var rememberToken: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("profile_url") var profileUrl: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("notifications") var notifications: ArrayList<String> = arrayListOf()

) : Parcelable