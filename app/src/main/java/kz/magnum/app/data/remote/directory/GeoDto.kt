package kz.magnum.app.data.remote.directory

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeoDto(
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
)
