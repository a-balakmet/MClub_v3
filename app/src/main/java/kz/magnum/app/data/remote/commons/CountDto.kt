package kz.magnum.app.data.remote.commons

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountDto(
    @SerialName("value") val value: Int
)
