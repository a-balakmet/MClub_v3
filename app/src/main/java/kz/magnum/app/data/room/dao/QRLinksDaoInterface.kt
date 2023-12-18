package kz.magnum.app.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kz.magnum.app.data.commonModels.QRLink
import kz.magnum.app.data.room.RoomNames

@Dao
interface QRLinksDaoInterface {

    @Query("SELECT * FROM ${RoomNames.qrLinks} ORDER BY `id` ASC")
    fun emitLinks(): Flow<List<QRLink>>
}