package com.project.tailor.utils

import com.project.tailor.api.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

/**
 * Wrap a suspending API [call] in try/catch. In case an exception is thrown, a [Result.Error] is
 * created based on the [errorMessage].
 */
suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
    return try {
        call()
    } catch (e: Exception) {
        // An exception was thrown when calling the API so we're converting this to an IOException
        e.printStackTrace()
        Result.Error(IOException(errorMessage, e))
    }
}


/**
 * Wrap a suspending API [call] in try/catch. In case an exception is thrown, a [Result.Error] is
 * created based on the [errorMessage].
 */
fun <T : Any> safeApiCallSync(call: () -> Result<T>, errorMessage: String): Result<T> {
    return try {
        call()
    } catch (e: Exception) {
        // An exception was thrown when calling the API so we're converting this to an IOException
        e.printStackTrace()
        Result.Error(IOException(errorMessage, e))
    }
}
/**
 * Used to convert a [HashMap] into [RequestBody] for put request API
 */
fun HashMap<String, Any>.mapToRequestBody(): RequestBody {
    val jsonObject = (this as Map<*, *>?)?.let { JSONObject(it) }
    return jsonObject.toString()
        .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
}