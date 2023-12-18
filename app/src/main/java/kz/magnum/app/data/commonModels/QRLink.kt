package kz.magnum.app.data.commonModels

import aab.lib.commons.data.room.BaseEntity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.magnum.app.data.room.RoomNames.qrLinks

@Serializable
@Entity(tableName = qrLinks)
data class QRLink(
    @SerialName("id")
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id")
    override val id: Int,
    @SerialName("name")
    @ColumnInfo(name = "name")
    override val name: String,
    @SerialName("image")
    @ColumnInfo(name = "image")
    val image: String,
    @SerialName("link")
    @ColumnInfo(name = "link")
    val link: String,
    @SerialName("slug")
    @ColumnInfo(name = "slug")
    val slug: String
): BaseEntity()
