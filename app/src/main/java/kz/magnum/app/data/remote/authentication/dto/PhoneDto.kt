package kz.magnum.app.data.remote.authentication.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhoneDto(
    @SerialName("phone")
    val phone: String
)
