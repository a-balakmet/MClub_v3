package kz.magnum.app.data.commonModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.data.room.RoomNames.categoryId
import kz.magnum.app.data.room.RoomNames.categoryImageLink
import kz.magnum.app.data.room.RoomNames.categoryName
import kz.magnum.app.data.room.RoomNames.discountsCategories

@Serializable
@Entity(tableName = discountsCategories)
data class DiscountCategory(
    @SerialName("id")
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = categoryId)
    override val id: Int,
    @SerialName("name")
    @ColumnInfo(name = categoryName)
    override val name: String,
    @SerialName("image_link")
    @ColumnInfo(name = categoryImageLink)
    val imageUrl: String,
    @SerialName("order")
    @ColumnInfo(name = "categoryOrder")
    val order: Int?
): BaseEntity()
