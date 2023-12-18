package kz.magnum.app.data.remote.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceInfoDto(
    @SerialName("app_build")
    val appBuild: String,
    @SerialName("app_version")
    val appVersion: String,
    @SerialName("device_id")
    val deviceId: String,
    @SerialName("is_expirable")
    val isExpired: Boolean,
    @SerialName("manufacturer")
    val manufacturer: String,
    @SerialName("model")
    val model: String,
    @SerialName("os_version")
    val osVersion: String,
    @SerialName("platform")
    val platform: String,
    @SerialName("push_token")
    val pushToken: String?
)
