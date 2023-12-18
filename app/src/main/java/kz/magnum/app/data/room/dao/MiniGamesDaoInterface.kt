package kz.magnum.app.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kz.magnum.app.data.room.RoomNames
import kz.magnum.app.data.room.entities.MiniGame

@Dao
interface MiniGamesDaoInterface {

    @Query("SELECT * FROM ${RoomNames.miniGames} ORDER BY `id` DESC")
    fun emitMiniGames(): Flow<List<MiniGame>>
}