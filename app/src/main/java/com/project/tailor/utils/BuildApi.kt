package com.project.tailor.utils

import com.google.gson.annotations.SerializedName

object BuildApi {
    @SerializedName("VERSION_NAME")
    var VERSION_NAME: String = "1.0.0"

    @SerializedName("RELEASE_TYPE")
    var RELEASE_TYPE: String = "dev"

    @SerializedName("DEVICE_ID")
    var DEVICE_ID: String = "android-device"

    @SerializedName("DEVICE_MODEL")
    var DEVICE_MODEL: String = "test-model"

    @SerializedName("DEVICE_TYPE")
    var DEVICE_TYPE: String = "android"

    fun setBasicParams(
        versionName: String,
        releaseCode: String,
        deviceId: String,
        deviceModel: String,
        deviceType: String
    ) {
        VERSION_NAME = versionName
        RELEASE_TYPE = releaseCode
        DEVICE_ID = deviceId
        DEVICE_MODEL = deviceModel
        DEVICE_TYPE = deviceType
    }
}
