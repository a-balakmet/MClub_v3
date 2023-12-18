package kz.magnum.app.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.data.room.RoomNames.shopTypes

@Entity(tableName = shopTypes)
data class ShopType(
    @PrimaryKey(autoGenerate = false) override val id: Int,
    @ColumnInfo(name = "name") override val name: String,
    @ColumnInfo(name = "icon") val icon: Int,
) : BaseEntity()
