package kz.magnum.app.data.remote.discounts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.magnum.app.data.commonModels.DiscountCategory

@Serializable
data class CategoryDiscountsDto(
    @SerialName("category") val category: DiscountCategory,
    @SerialName("discounts") val discounts: List<DiscountDto>
)
