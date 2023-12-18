package kz.magnum.app.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import aab.lib.commons.data.room.BaseDao
import kz.magnum.app.data.room.RoomNames.versions
import kz.magnum.app.data.room.entities.Version

@Dao
abstract class VersionsDao : BaseDao<Version>(versions){

    @Query("SELECT * FROM $versions WHERE name LIKE :search")
    abstract suspend fun getItemByName(search: String): Version?
}

