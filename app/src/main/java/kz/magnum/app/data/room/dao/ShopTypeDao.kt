package kz.magnum.app.data.room.dao

import androidx.room.Dao
import aab.lib.commons.data.room.BaseDao
import kz.magnum.app.data.room.RoomNames.shopTypes
import kz.magnum.app.data.room.entities.ShopType

@Dao
abstract class ShopTypeDao : BaseDao<ShopType>(tableName = shopTypes)