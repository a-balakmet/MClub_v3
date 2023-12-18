package kz.magnum.app.data.room.dao

import aab.lib.commons.data.room.BaseDao
import androidx.room.Dao
import kz.magnum.app.data.room.RoomNames.miniGames
import kz.magnum.app.data.room.entities.MiniGame

@Dao
abstract class MiniGamesDao : BaseDao<MiniGame>(tableName = miniGames), MiniGamesDaoInterface