package kz.magnum.app.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.room.Upsert
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kz.magnum.app.data.room.RoomNames
import kz.magnum.app.data.room.entities.StoriesViewed

@Dao
abstract class StoriesViewedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: StoriesViewed)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertList(entities: List<StoriesViewed>)

    @Upsert
    abstract suspend fun upsert(entity: StoriesViewed)

    @Upsert
    abstract suspend fun upsertAll(entities: List<StoriesViewed>)

    @Update
    abstract suspend fun update(entity: StoriesViewed)

    @Update
    abstract suspend fun updateList(entity: List<StoriesViewed>)

    @Delete
    abstract suspend fun delete(entity: StoriesViewed)

    @Delete
    abstract suspend fun deleteList(entity: List<StoriesViewed>)

    @RawQuery
    protected abstract fun deleteAll(query: SupportSQLiteQuery): Int

    fun deleteAll() {
        val query = SimpleSQLiteQuery("DELETE FROM ${RoomNames.viewsStories}")
        deleteAll(query)
    }

    @Query("SELECT * FROM ${RoomNames.viewsStories} WHERE uploaded LIKE :uploaded")
    abstract suspend fun getIsUploaded(uploaded: Boolean): StoriesViewed

    @RawQuery
    protected abstract suspend fun getAll(query: SupportSQLiteQuery): List<StoriesViewed>

    suspend fun getAll(): List<StoriesViewed> {
        val query = SimpleSQLiteQuery("SELECT * FROM ${RoomNames.viewsStories}")
        return getAll(query)
    }
}