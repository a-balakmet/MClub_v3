package kz.magnum.app.data.remote.discounts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.magnum.app.data.commonModels.DiscountCategory
import kz.magnum.app.data.commonModels.DiscountType

@Serializable
data class DiscountsDataDto(
    @SerialName("categories") val categories: List<DiscountCategory>,
    @SerialName("types") val types: List<DiscountType>,
    @SerialName("category_to_discounts") val categoryDiscounts: List<CategoryDiscountsDto>,
)
