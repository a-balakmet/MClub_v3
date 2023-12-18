package kz.magnum.app.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kz.magnum.app.data.room.RoomNames
import kz.magnum.app.data.room.entities.BonusCard

@Dao
interface BonusCardDaoInterface {

    @Query("SELECT * FROM ${RoomNames.cards} ORDER BY `order` ASC")
    fun emitCards(): Flow<List<BonusCard>>
}