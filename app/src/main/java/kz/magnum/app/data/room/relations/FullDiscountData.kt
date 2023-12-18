package kz.magnum.app.data.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import kz.magnum.app.data.commonModels.DiscountCategory
import kz.magnum.app.data.room.RoomNames.categoryId
import kz.magnum.app.data.room.RoomNames
import kz.magnum.app.data.room.entities.Discount

data class FullDiscountData(
    @Embedded val category: DiscountCategory,
    @Relation(
        entity = Discount::class,
        parentColumn = categoryId,
        entityColumn = RoomNames.discounts
    )
    val discounts: List<Discount>
)