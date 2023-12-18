package kz.magnum.app.data.room.dao

import aab.lib.commons.data.room.BaseDao
import androidx.room.Dao
import kz.magnum.app.data.commonModels.ShoppingIcons
import kz.magnum.app.data.room.RoomNames.shoppingIcons

@Dao
abstract class ShoppingIconsDao : BaseDao<ShoppingIcons>(tableName = shoppingIcons)
