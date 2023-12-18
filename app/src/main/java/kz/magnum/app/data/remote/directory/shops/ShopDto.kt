package kz.magnum.app.data.remote.directory.shops

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import aab.lib.commons.data.room.BaseEntity
import aab.lib.commons.extensions.StringConvert
import aab.lib.commons.extensions.StringConvert.toDateTime
import aab.lib.commons.extensions.twoDigitsString
import kz.magnum.app.data.remote.directory.GeoDto
import kz.magnum.app.data.room.entities.City
import kz.magnum.app.data.room.entities.Shop

@Serializable
data class ShopDto(
    @SerialName("id")
    override val id: Int,
    @SerialName("name")
    override val name: String,
    @SerialName("address")
    val address: String?,
    @SerialName("additional_props")
    val properties: List<ShopPropertyDto>,
    @SerialName("city_id")
    val cityId: Int,
    @SerialName("geo")
    val geo: GeoDto?,
    @SerialName("open_hour")
    val openHour: String,
    @SerialName("close_hour")
    val closeHour: String,
    @SerialName("qr_code")
    val qrCode: String?,
    @SerialName("type")
    val type: String,
) : BaseEntity()

fun ShopDto.toShop(city: City?): Shop {

    fun String.getHour(): String {
        val d = this.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter)
        return "${d.hour.twoDigitsString()}:${d.minute.twoDigitsString()}"
    }

    return Shop(
        id = id,
        name = name,
        lat = geo?.latitude ?: 0.0,
        lng = geo?.longitude ?: 0.0,
        address = address ?: "",
        qrCode = qrCode ?: "",
        type = type,
        cityId = cityId,
        city = city,
        openHour = openHour.getHour(),
        closeHour = closeHour.getHour(),
    )
}