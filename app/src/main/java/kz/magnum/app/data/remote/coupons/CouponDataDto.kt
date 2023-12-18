package kz.magnum.app.data.remote.coupons

import aab.lib.commons.extensions.StringConvert
import aab.lib.commons.extensions.StringConvert.toDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.magnum.app.R
import kz.magnum.app.domain.models.Coupon

@Serializable
data class CouponDataDto(
    @SerialName("id") val id: Int,
    @SerialName("coupon") val coupon: CouponDto,
    @SerialName("status") val status: Int,
    @SerialName("dt_active_from") val activeFrom: String,
    @SerialName("dt_active_to") val activeTo: String
)

fun CouponDataDto.toCoupon(): Coupon {

    fun stringState() = when (status) {
        1 -> "available_coupon"
        2 -> "activated_coupon2"
        3 -> "used"
        else -> "burned2"
    }

    fun color() = when (status) {
        1 -> R.color.app_green_color
        2 -> R.color.app_blue_color
        3 -> R.color.app_light_gray_color
        else -> R.color.splash_color
    }

    return Coupon(
        id = id,
        name = coupon.name,
        couponId = coupon.id,
        subTitle = coupon.subtitle,
        type = coupon.type,
        description = coupon.description,
        instructions = coupon.instructions,
        value = coupon.value,
        imageLink = coupon.imageUrl,
        stateInt = status,
        stateString = stringState(),
        couponColor = color(),
        dateFrom = activeFrom.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter),
        dateTo = activeTo.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter),
        withProducts = coupon.withProductsList ?: false
    )
}