package kz.magnum.app.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.data.room.RoomNames.shopProperties
import kz.magnum.app.data.room.RoomNames.shopPropertyId

@Serializable
@Entity(tableName = shopProperties)
data class ShopProperty(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = shopPropertyId) override val id: Int,
    override val name: String,
    val type: String,
    val label: String,
    val icon: Int
) : BaseEntity()
