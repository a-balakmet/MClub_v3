package kz.magnum.app.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.data.room.RoomNames

@Dao
interface ClubDaoInterface {

    @Query("SELECT * FROM ${RoomNames.clubs} ORDER BY `id` DESC")
    fun emitClubs(): Flow<List<Club>>

    @Query("SELECT * FROM ${RoomNames.clubs} WHERE id = :itemId")
    fun emitClub(itemId: Int): Flow<Club?>
}