package kz.magnum.app.data.commonModels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscountTerms(
    @SerialName("percentage") val percentage: Int,
    @SerialName("price_new") val newPrice: Int,
    @SerialName("price_old") val oldPrice: Int
)
