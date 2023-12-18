package kz.magnum.app.data.room.dao

import androidx.room.Dao
import aab.lib.commons.data.room.BaseDao
import kz.magnum.app.data.room.RoomNames.storiesScreens
import kz.magnum.app.data.room.entities.StoryScreen

@Dao
abstract class StoriesScreensDao : BaseDao<StoryScreen>(tableName = storiesScreens)