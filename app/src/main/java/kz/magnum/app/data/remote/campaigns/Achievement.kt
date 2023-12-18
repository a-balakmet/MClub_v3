package kz.magnum.app.data.remote.campaigns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Achievement(
    @SerialName("conditions") val conditions: List<AchievementConditions>,
    @SerialName("description") val description: String,
    @SerialName("dt_created") val creationDate: String,
    @SerialName("dt_end") val endDate: String,
    @SerialName("dt_start") val startDate: String,
    @SerialName("empty_image") val emptyImage: String,
    @SerialName("full_image") val fullImage: String,
    @SerialName("id") val id: Int,
    @SerialName("merchant_id") val merchantId: Int,
    @SerialName("name") val name: String,
    @SerialName("sticker_image") val stickerImage: String?,
    @SerialName("sticker_name") val stickerName: String?
)
