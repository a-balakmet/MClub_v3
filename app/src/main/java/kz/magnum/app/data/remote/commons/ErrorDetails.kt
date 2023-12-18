package kz.magnum.app.data.remote.commons

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorDetails(
    @SerialName("error_code")
    val code: String,
    @SerialName("error_message")
    val message: String
)