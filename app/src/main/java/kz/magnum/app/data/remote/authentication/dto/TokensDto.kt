package kz.magnum.app.data.remote.authentication.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokensDto(
    @SerialName("auth_token")
    val authToken: String,
    @SerialName("refresh_token")
    val refreshToken: String,
)