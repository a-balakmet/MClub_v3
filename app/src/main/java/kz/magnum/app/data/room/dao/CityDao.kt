package kz.magnum.app.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import aab.lib.commons.data.room.BaseDao
import kz.magnum.app.data.room.RoomNames.cities
import kz.magnum.app.data.room.entities.City

@Dao
abstract class CityDao : BaseDao<City>(tableName = cities) {

    @Query("SELECT * FROM $cities WHERE cityId = :id")
    abstract suspend fun getCityById(id: Int): City
}