package kz.magnum.app.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kz.magnum.app.data.room.RoomNames
import kz.magnum.app.data.room.entities.Translation

@Dao
interface TranslationDaoInterface {

    @Query("SELECT * FROM ${RoomNames.translations}")
    fun emitTranslations(): Flow<List<Translation>>
    @Query("SELECT * FROM ${RoomNames.translations} WHERE name = :name")
    fun emitTranslation(name: String): Flow<Translation?>
}