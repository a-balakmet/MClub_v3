package kz.magnum.app.data.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import kz.magnum.app.data.room.RoomNames.cityId
import kz.magnum.app.data.room.entities.City
import kz.magnum.app.data.room.entities.Shop

data class FullShopData(
    @Embedded val city: City,
    @Relation(
        entity = Shop::class,
        parentColumn = cityId,
        entityColumn = "shop_${cityId}"
    )
    val shops: List<ShopWithProperties>
)