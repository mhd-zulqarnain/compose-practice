package com.project.tailor.api

import com.project.tailor.utils.BuildApi
import java.util.*
import kotlin.collections.HashMap

object ApiParams {

     fun loginParams(email:String, password:String , clientSecret:String ): HashMap<String, Any> {
        val loginParams: HashMap<String, Any> = HashMap()
         loginParams["email"] = email
         loginParams["password"] =password
         loginParams["client_id"] = 2
         loginParams["client_secret"] = clientSecret
         loginParams["grant_type"] = "password"
        return loginParams
    }

    fun signUpParams(email:String, password:String , name:String ): HashMap<String, Any> {
        val loginParams: HashMap<String, Any> = HashMap()
         loginParams["email"] = email
         loginParams["password"] =password
         loginParams["password_confirmation"] =password
         loginParams["name"] = name
        return loginParams
    }

    fun socialParams(token:String): HashMap<String, Any> {
        val socialParams: HashMap<String, Any> = HashMap()
        socialParams["id_token"] = token
        return socialParams
    }

}