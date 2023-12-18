package kz.magnum.app.data.remote.coupons

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import aab.lib.commons.data.room.BaseEntity

@Serializable
data class CouponDto(
    @SerialName("id") override val id: Int,
    @SerialName("title_ml") override val name: String,
    @SerialName("subtitle_ml") val subtitle: String,
    @SerialName("description_ml") val description: String,
    @SerialName("have_product_lists") val withProductsList: Boolean?,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("instructions_ml") val instructions: String,
    @SerialName("type") val type: String,
    @SerialName("value") val value: String
) : BaseEntity()
