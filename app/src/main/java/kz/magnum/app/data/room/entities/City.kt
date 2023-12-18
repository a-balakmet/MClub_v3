package kz.magnum.app.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.data.room.RoomNames.cities
import kz.magnum.app.data.room.RoomNames.cityId

@Serializable
@Entity(tableName = cities)
data class City(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = cityId) override val id: Int,
    @ColumnInfo(name = "cityName") override val name: String,
    @ColumnInfo(name = "cityLat") val lat: Double,
    @ColumnInfo(name = "cityLng") val lng: Double,
) : BaseEntity()
