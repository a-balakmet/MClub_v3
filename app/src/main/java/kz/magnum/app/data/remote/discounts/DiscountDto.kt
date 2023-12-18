package kz.magnum.app.data.remote.discounts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import aab.lib.commons.data.room.BaseEntity
import aab.lib.commons.extensions.StringConvert
import aab.lib.commons.extensions.StringConvert.toDateTime
import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.data.commonModels.DiscountCategory
import kz.magnum.app.data.commonModels.DiscountTerms
import kz.magnum.app.data.room.entities.Discount
import kz.magnum.app.data.room.relations.ShopWithProperties

@Serializable
data class DiscountDto(
    @SerialName("id") override val id: Int,
    @SerialName("name") override val name: String,
    @SerialName("dt_start") val dateStart: String,
    @SerialName("dt_end") val dateEnd: String,
    @SerialName("label_kz") val kzName: String?,
    @SerialName("actor_ids") val shopsIds: List<Int>?,
    @SerialName("category_id") val categoryId: Int,
    @SerialName("type_id") val typeId: Int,
    @SerialName("club") val club: Club?,
    @SerialName("discount_terms") val discountTerms: List<DiscountTerms>,
    @SerialName("image_link") val imageUrl: String?,
    @SerialName("is_best_price") val isBestPrice: Boolean,
    @SerialName("ware_reference") val wareReference: String
) : BaseEntity()

fun DiscountDto.toDiscount(
    category: DiscountCategory,
    shops: List<ShopWithProperties>
): Discount {
    return Discount(
        id = id,
        name = name,
        dateStart = dateStart.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter),
        dateEnd = dateEnd.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter),
        kzName = kzName,
        shops = shops,
        categoryId = categoryId,
        category = category,
        typeId = typeId,
        club = club,
        discountTerms = discountTerms[0],
        imageUrl = imageUrl,
        isBestPrice = isBestPrice,
        wareReference = wareReference
    )
}