package kz.magnum.app.data.room.entities

import aab.lib.commons.data.room.BaseEntity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kz.magnum.app.data.room.RoomNames.translations

@Entity(tableName = translations)
@Serializable
data class Translation(
    @PrimaryKey(autoGenerate = true) override val id: Int,
    @ColumnInfo(name = "name") override val name: String,
    @ColumnInfo(name = "ru")  val ru: String,
    @ColumnInfo(name = "kk") val kk: String,
    @ColumnInfo(name = "en") val en: String,
): BaseEntity()
