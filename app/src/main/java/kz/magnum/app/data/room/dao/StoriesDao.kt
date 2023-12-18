package kz.magnum.app.data.room.dao

import androidx.room.Dao
import aab.lib.commons.data.room.BaseDao
import kz.magnum.app.data.room.RoomNames.stories
import kz.magnum.app.data.room.entities.Story

@Dao
abstract class StoriesDao : BaseDao<Story>(tableName = stories)