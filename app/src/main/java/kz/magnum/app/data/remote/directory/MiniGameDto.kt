package kz.magnum.app.data.remote.directory

import aab.lib.commons.data.room.BaseEntity
import aab.lib.commons.extensions.StringConvert
import aab.lib.commons.extensions.StringConvert.toDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.magnum.app.data.room.entities.MiniGame

@Serializable
data class MiniGameDto(
    @SerialName("id") override val id: Int,
    @SerialName("name") override val name: String,
    @SerialName("active") val active: Boolean,
    @SerialName("authorization") val authorization: Boolean,
    @SerialName("base_endpoint") val gameUrl: String,
    @SerialName("dt_created") val dateCreated: String,
    @SerialName("dt_end") val dateEnd: String,
    @SerialName("dt_start") val dateStart: String,
    @SerialName("dt_updated") val dateUpdated: String,
    @SerialName("image") val imageUrl: String,
    @SerialName("merchant_id") val merchantID: Int,
    @SerialName("sort_order") val sortOrder: Int?,
    @SerialName("type") val type: String
) : BaseEntity()

fun MiniGameDto.toMiniGame(): MiniGame {
    return MiniGame(
        id = id,
        name = name,
        active = active,
        authorization = authorization,
        gameUrl = gameUrl,
        dateCreated = dateCreated.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter),
        dateEnd = dateEnd.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter),
        dateStart = dateStart.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter),
        dateUpdated = dateUpdated.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter),
        imageUrl = imageUrl,
        merchantID = merchantID,
        sortOrder = sortOrder,
        type = type
    )
}