package kz.magnum.app.data.remote.campaigns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AchievementPrices(
    @SerialName("new_price") val newPrice: Int,
    @SerialName("old_price") val oldPrice: Int
)
