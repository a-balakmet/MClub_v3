package kz.magnum.app.data.commonModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.data.room.RoomNames.discountsTypes
import kz.magnum.app.data.room.RoomNames.typeId
import kz.magnum.app.data.room.RoomNames.typeImageLink
import kz.magnum.app.data.room.RoomNames.typeName

@Serializable
@Entity(tableName = discountsTypes)
data class DiscountType(
    @SerialName("id")
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = typeId)
    override val id: Int,
    @SerialName("name")
    @ColumnInfo(name = typeName)
    override val name: String,
    @SerialName("image_link")
    @ColumnInfo(name = typeImageLink)
    val imageUrl: String,
    @SerialName("order")
    @ColumnInfo(name = "typeOrder")
    val order: Int?
): BaseEntity()
