package kz.magnum.app.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.data.room.RoomNames
import kz.magnum.app.data.room.RoomNames.cityId
import kz.magnum.app.data.room.RoomNames.shopId
import kz.magnum.app.data.room.RoomNames.shops

@Serializable
@Entity(
    tableName = shops,
    indices = [Index(cityId)],
    foreignKeys = [
        ForeignKey(entity = City::class, childColumns = [cityId], parentColumns = [cityId], onDelete = CASCADE),
    ]
)
data class Shop(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = shopId) override val id: Int,
    @ColumnInfo(name = "name") override val name: String,
    @ColumnInfo(name = "shopLat") val lat: Double,
    @ColumnInfo(name = "shopLng") val lng: Double,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "qrCode") val qrCode: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "shop_${RoomNames.cityId}") val cityId: Int,
    @Embedded val city: City?,
    @ColumnInfo(name = "openHour") val openHour: String,
    @ColumnInfo(name = "closeHour") val closeHour: String,
) : BaseEntity()
