package kz.magnum.app.data.room.entities

import aab.lib.commons.data.room.BaseEntity
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kz.magnum.app.data.room.RoomConverter
import kz.magnum.app.data.room.RoomNames.miniGames
import java.time.LocalDateTime

@Entity(tableName = miniGames)
data class MiniGame(
    @PrimaryKey(autoGenerate = false) override val id: Int,
    override val name: String,
    val active: Boolean,
    val authorization: Boolean,
    val gameUrl: String,
    @TypeConverters(RoomConverter::class) val dateCreated: LocalDateTime,
    @TypeConverters(RoomConverter::class) val dateEnd: LocalDateTime,
    @TypeConverters(RoomConverter::class) val dateStart: LocalDateTime,
    @TypeConverters(RoomConverter::class) val dateUpdated: LocalDateTime,
    val imageUrl: String,
    val merchantID: Int,
    val sortOrder: Int?,
    val type: String
) : BaseEntity()
