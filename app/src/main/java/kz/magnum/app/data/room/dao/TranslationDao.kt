package kz.magnum.app.data.room.dao

import aab.lib.commons.data.room.BaseDao
import androidx.room.Dao
import androidx.room.Query
import kz.magnum.app.data.room.RoomNames.translations
import kz.magnum.app.data.room.entities.Translation

@Dao
abstract class TranslationDao : BaseDao<Translation>(translations), TranslationDaoInterface {


    @Query("SELECT * FROM $translations WHERE name = :name")
    abstract suspend fun getByName(name: String): Translation

    @Query("SELECT * FROM $translations WHERE name LIKE :name")
    abstract fun getTranslation(name: String): Translation?
}