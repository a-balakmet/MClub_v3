package kz.magnum.app.data.commonModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.data.room.RoomNames.clubs

@Serializable
@Entity(tableName = clubs)
data class Club(
    @SerialName("id")
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id")
    override val id: Int,
    @SerialName("name_ml")
    @ColumnInfo(name = "name")
    override val name: String,
    @SerialName("is_adult")
    @ColumnInfo(name = "isAdult")
    val isAdult: Boolean,
    @SerialName("membership")
    @ColumnInfo(name = "isMember")
    val isMember: Boolean,
    @SerialName("reference")
    @ColumnInfo(name = "reference")
    val reference: String,
    @SerialName("description_ml")
    @ColumnInfo(name = "description")
    val description: String,
    @SerialName("document_url_ml")
    @ColumnInfo(name = "documentUrl")
    val documentUrl: String,
    @SerialName("external_url")
    @ColumnInfo(name = "externalUrl")
    val externalUrl: String,
    @SerialName("merchant_id")
    @ColumnInfo(name = "merchantId")
    val merchantId: Int,
    @SerialName("thumbnail_url_ml")
    @ColumnInfo(name = "thumbnailUrl")
    val thumbnailUrl: String,
    @SerialName("image_url_ml")
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
) : BaseEntity()
