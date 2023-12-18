package kz.magnum.app.data.room.dao

import androidx.room.Dao
import aab.lib.commons.data.room.BaseDao
import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.data.room.RoomNames.clubs

@Dao
abstract class ClubsDao : BaseDao<Club>(tableName = clubs), ClubDaoInterface
