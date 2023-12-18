package kz.magnum.app.data.room.dao

import androidx.room.Dao
import aab.lib.commons.data.room.BaseDao
import kz.magnum.app.data.commonModels.DiscountType
import kz.magnum.app.data.room.RoomNames.discountsTypes

@Dao
abstract class DiscountsTypesDao : BaseDao<DiscountType>(tableName = discountsTypes)