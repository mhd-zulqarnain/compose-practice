package com.project.tailor.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.tailor.api.Envelope
import com.project.tailor.api.Result

import org.json.JSONObject
import retrofit2.Response
import java.lang.reflect.Type

/**
 * Used to convert a [Envelope]<[Response]> received from Retrofit into [Result] for our UI layer
 */
fun <T : Any> Response<Envelope<T>>.processResult(): Result<T> {
    if (this.isSuccessful) {
        val body = this.body()
        if (body != null) {
            if (body.header.code == 200)
                return Result.Success(body.body)
            return Result.Error(ApiException(body.header.message, body.header.code))
        }
    } else if (this.code() == 400) {
        return try {
            val type: Type =
                object : TypeToken<Envelope<Any>>() {}.type
            val errorBody = Gson().fromJson<Envelope<Any>>(
                this.errorBody()?.string(),
                type
            )
            Result.Error(ApiException(errorBody.header.message, errorBody.header.code))
        } catch (e: Exception) {
            Result.Error(
                NetworkException(
                    "Something went wrong, Please try again later or contact support: ${this.code()} ${this.message()}",
                    this.code()
                )
            )
        }

    }
    return Result.Error(
        NetworkException(
            "Something went wrong, Please try again later or contact support: ${this.code()} ${this.message()}",
            this.code()
        )
    )
}


/**
 * Used to convert a [Response] received from Retrofit into [Result] for our UI layer
 */
fun <T : Any> Response<T>.processResponse(): Result<T> {
    return when {
        this.isSuccessful -> {
            this.body()?.let {
                Result.Success(it)
            } ?: run {
                Result.Error(ApiException(this.message(), this.code()))
            }
        }
        this.code() == 400 -> {
            return try {
                val errorBody = JSONObject(this.errorBody()?.string() ?: "")
                val message = if (errorBody.has("error_description"))
                    errorBody.getString("error_description") else ""
                val code = if (errorBody.has("response_code"))
                    errorBody.getInt("response_code") else this.code()
                Result.Error(ApiException(message, code))
            } catch (e: Exception) {
                Result.Error(
                    NetworkException(
                        "Something went wrong, Please try again later or contact support: ${this.code()} ${this.message()}",
                        this.code()
                    )
                )
            }
        }
        else -> Result.Error(
            NetworkException(
                "Something went wrong, Please try again later or contact support: ${this.code()} ${this.message()}",
                this.code()
            )
        )
    }
}