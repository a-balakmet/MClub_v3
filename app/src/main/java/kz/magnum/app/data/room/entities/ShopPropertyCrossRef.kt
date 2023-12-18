package kz.magnum.app.data.room.entities

import androidx.room.Entity
import kz.magnum.app.data.room.RoomNames

@Entity(
    tableName = RoomNames.shopPropertiesCrossRef,
    primaryKeys = [RoomNames.shopId, RoomNames.shopPropertyId]
)
data class ShopPropertyCrossRef(
    val shopId: Int,
    val shopPropertyId: Int
)
