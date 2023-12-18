package kz.magnum.app.data.room.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import kotlinx.serialization.Serializable
import kz.magnum.app.data.room.RoomNames.shopId
import kz.magnum.app.data.room.RoomNames.shopPropertyId
import kz.magnum.app.data.room.entities.Shop
import kz.magnum.app.data.room.entities.ShopProperty
import kz.magnum.app.data.room.entities.ShopPropertyCrossRef

@Serializable
data class ShopWithProperties(
    @Embedded val shop: Shop,
    @Relation(
        parentColumn = shopId,
        entityColumn = shopPropertyId,
        associateBy = Junction(ShopPropertyCrossRef::class)
    )
    val properties: List<ShopProperty>
)
