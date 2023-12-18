package kz.magnum.app.data.room.entities

import aab.lib.commons.data.room.BaseEntity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kz.magnum.app.data.room.RoomConverter
import kz.magnum.app.data.room.RoomNames.cards
import java.time.LocalDateTime

@Entity(tableName = cards)
data class BonusCard(
    @PrimaryKey(autoGenerate = false) override val id: Int,
    @ColumnInfo(name = "cardNumber") override val name: String,
    val cardNumberNum: Long,
    @TypeConverters(RoomConverter::class) val dateCreated: LocalDateTime?,
    val balance: String,
    val barcode: String,
    val type: Int,
    val isActive: Boolean,
    val status: Int,
    var order: Int
): BaseEntity()