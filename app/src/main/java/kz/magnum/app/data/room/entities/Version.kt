package kz.magnum.app.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.data.room.RoomConverter
import kz.magnum.app.data.room.RoomNames.versions
import java.time.LocalDateTime

@Entity(tableName = versions)
data class Version(
    @PrimaryKey(autoGenerate = true) override val id: Int,
    @ColumnInfo(name = "name") override val name: String,
    @ColumnInfo(name = "date")
    @TypeConverters(RoomConverter::class)
    val date: LocalDateTime
): BaseEntity()
