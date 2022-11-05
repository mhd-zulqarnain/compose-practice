package com.project.tailor.storage

/**
 * Interface representing storage
 */

interface Storage {
    fun setString(key: String, value: String)
    fun setBoolean(key: String, value: Boolean)
    fun setInt(key: String, value: Int)
    fun getString(key: String, defaultValue: String = ""): String
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    fun getInt(key: String,defaultValue:Int=-1): Int
    fun remove(key: String)
}