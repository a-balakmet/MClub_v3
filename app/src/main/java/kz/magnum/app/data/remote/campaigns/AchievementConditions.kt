package kz.magnum.app.data.remote.campaigns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.magnum.app.data.remote.coupons.CouponDto

@Serializable
data class AchievementConditions(
    @SerialName ("additional_info") val achievementPrices: AchievementPrices,
    @SerialName ("cost") val cost: Int,
    @SerialName ("coupon") val coupon: CouponDto,
    @SerialName ("limit_per_user") val limit: Int,
    @SerialName ("name") val name: String,
    @SerialName ("type") val type: Int,
    @SerialName ("used_count") val usedCount: Int,
    @SerialName ("uuid") val uuid: String
)
