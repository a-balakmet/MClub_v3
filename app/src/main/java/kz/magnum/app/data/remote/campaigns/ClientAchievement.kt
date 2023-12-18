package kz.magnum.app.data.remote.campaigns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClientAchievement(
    @SerialName("achievement") val achievement: Achievement,
    @SerialName("stickers_count") val stickersCount: Int
)
