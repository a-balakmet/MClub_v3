package kz.magnum.app.data.remote.campaigns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MonthProgressDto(
    @SerialName("cases") val cases: List<Case>,
    @SerialName("is_enabled") val isEnabled: Boolean,
    @SerialName("total_sum") val totalSum: Int
)

@Serializable
data class Case(
    @SerialName("amount") val amount: Int,
    @SerialName("description") val description: String,
    @SerialName("percent") val percent: Float
)
