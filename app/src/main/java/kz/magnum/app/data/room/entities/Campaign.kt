package kz.magnum.app.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.data.room.RoomConverter
import kz.magnum.app.data.room.RoomNames.campaigns
import java.time.LocalDateTime

@Entity(tableName = campaigns)
data class Campaign(
    @PrimaryKey(autoGenerate = false) override val id: Int,
    override val name: String,
    val description: String,
    val landscapeImage: String,
    val squareImage: String,
    @TypeConverters(RoomConverter::class) val badges: List<String>?,
    @TypeConverters(RoomConverter::class) val start: LocalDateTime,
    @TypeConverters(RoomConverter::class) val stop: LocalDateTime,
) : BaseEntity()
