package kz.magnum.app.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.RoomWarnings
import androidx.room.Transaction
import aab.lib.commons.data.room.BaseDao
import kz.magnum.app.data.room.RoomNames.shopPropertiesCrossRef
import kz.magnum.app.data.room.RoomNames.shops
import kz.magnum.app.data.room.entities.Shop
import kz.magnum.app.data.room.entities.ShopPropertyCrossRef
import kz.magnum.app.data.room.relations.FullShopData
import kz.magnum.app.data.room.relations.ShopWithProperties

@Dao
abstract class ShopsDao : BaseDao<Shop>(tableName = shops) {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertPropertyCrossRef(join: ShopPropertyCrossRef)

    @Query("DELETE FROM $shopPropertiesCrossRef")
    abstract suspend fun deleteShopPropertiesRef()

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM shops WHERE shop_cityId = :cityId")
    abstract suspend fun getShopsByCity(cityId: Int): List<FullShopData>

    @Transaction
    @Query("SELECT * FROM shops WHERE shopId = :shopId")
    abstract suspend fun getShopById(shopId: Int): ShopWithProperties

    @Transaction
    @Query("SELECT * FROM shops WHERE shopId IN (:shopsIds)")
    abstract suspend fun getShopsByIds(shopsIds: List<Int>): List<ShopWithProperties>

    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM shops WHERE type = :type AND shop_cityId = :cityId")
    abstract suspend fun getShopsByCityAndType(type: String, cityId: Int): List<FullShopData>

    @Transaction
    @Query("SELECT * FROM shops " +
            "WHERE shop_cityId = :cityId " +
            "AND (:type IS NULL OR type = :type) ")
    abstract suspend fun getShopsWithFilter(cityId: Int, type: String?): List<ShopWithProperties>
}