package kz.magnum.app.data.remote.directory

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.data.room.entities.City

@Serializable
data class CityDto(
    @SerialName("id") override val id: Int,
    @SerialName("name") override val name: String,
    @SerialName("geo") val geo: GeoDto?
) : BaseEntity()

fun CityDto.toCity() = City(id = id, name = name, lat = geo?.latitude ?: 0.0, lng = geo?.longitude ?: 0.0)