package kz.magnum.app.data.room.dao

import androidx.room.Dao
import aab.lib.commons.data.room.BaseDao
import kz.magnum.app.data.room.RoomNames.shopProperties
import kz.magnum.app.data.room.entities.ShopProperty

@Dao
abstract class ShopPropertiesDao : BaseDao<ShopProperty>(tableName = shopProperties)