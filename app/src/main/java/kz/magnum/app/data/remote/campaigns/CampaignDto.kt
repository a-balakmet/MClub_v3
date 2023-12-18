package kz.magnum.app.data.remote.campaigns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import aab.lib.commons.data.room.BaseEntity
import aab.lib.commons.extensions.StringConvert
import aab.lib.commons.extensions.StringConvert.toDateTime
import kz.magnum.app.data.room.entities.Campaign

@Serializable
data class CampaignDto(
    @SerialName("id") override val id: Int,
    @SerialName("title") override val name: String,
    @SerialName("short_description") val description: String,
    @SerialName("image_vertical_url") val landscapeImage: String,
    @SerialName("image_url") val squareImage: String,
    @SerialName("start") val start: String,
    @SerialName("stop") val stop: String,
    @SerialName("badges") val badges: List<String>?,
): BaseEntity()

fun CampaignDto.toCampaign() = Campaign(
    id = id,
    name = name,
    description = description,
    landscapeImage = landscapeImage,
    squareImage = squareImage,
    badges = badges,
    start = start.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter),
    stop = stop.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter),
)