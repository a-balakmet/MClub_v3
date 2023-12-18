package kz.magnum.app.data.room.dao

import androidx.room.Dao
import aab.lib.commons.data.room.BaseDao
import kz.magnum.app.data.room.RoomNames.discounts
import kz.magnum.app.data.room.entities.Discount

@Dao
abstract class DiscountsDao : BaseDao<Discount>(tableName = discounts)