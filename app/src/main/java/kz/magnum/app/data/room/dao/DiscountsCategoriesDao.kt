package kz.magnum.app.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import aab.lib.commons.data.room.BaseDao
import kz.magnum.app.data.commonModels.DiscountCategory
import kz.magnum.app.data.room.RoomNames.discountsCategories

@Dao
abstract class DiscountsCategoriesDao : BaseDao<DiscountCategory>(tableName = discountsCategories) {

    @Query("SELECT * FROM $discountsCategories WHERE categoryId = :id")
    abstract suspend fun getCategoryById(id: Int): DiscountCategory
}
