package kz.magnum.app.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.data.commonModels.StoryAction
import kz.magnum.app.data.room.RoomConverter
import kz.magnum.app.data.room.RoomNames
import kz.magnum.app.data.room.RoomNames.childStoryId
import kz.magnum.app.data.room.RoomNames.childStoryName
import kz.magnum.app.data.room.RoomNames.storiesScreens

@Entity(tableName = storiesScreens,
    indices = [Index(RoomNames.parentStoryId)],
    foreignKeys = [
        ForeignKey(entity = Story::class, childColumns = [RoomNames.parentStoryId], parentColumns = [RoomNames.parentStoryId], onDelete = ForeignKey.CASCADE),
    ]
)
data class StoryScreen(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = childStoryId) override val id: Int,
    @ColumnInfo(name = childStoryName) override val name: String,
    @ColumnInfo(name = "story_${RoomNames.parentStoryId}") val parentStoryId: Int,
    @Embedded val story: Story,
    val screenOrder: Int?,
    @TypeConverters(RoomConverter::class) val actions: List<StoryAction>?,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val image: ByteArray? = null,
    val isViewedScreen: Boolean
): BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StoryScreen

        if (id != other.id) return false
        if (name != other.name) return false
        if (parentStoryId != other.parentStoryId) return false
        if (story != other.story) return false
        if (screenOrder != other.screenOrder) return false
        if (actions != other.actions) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (isViewedScreen != other.isViewedScreen) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + parentStoryId
        result = 31 * result + story.hashCode()
        result = 31 * result + (screenOrder ?: 0)
        result = 31 * result + (actions?.hashCode() ?: 0)
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + isViewedScreen.hashCode()
        return result
    }

}
