package kz.magnum.app.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kz.magnum.app.data.room.RoomNames
import kz.magnum.app.data.room.entities.Campaign

@Dao
interface CampaignDaoInterface {

    @Query("SELECT * FROM ${RoomNames.campaigns} ORDER BY `id` DESC")
    fun emitCampaigns(): Flow<List<Campaign>>
}