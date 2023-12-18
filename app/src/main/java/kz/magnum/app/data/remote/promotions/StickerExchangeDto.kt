package kz.magnum.app.data.remote.promotions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StickerExchangeDto(
    @SerialName("achievement_condition_uuid") val uuid: String,
    @SerialName("achievement_id") val achievementID: Int
)
