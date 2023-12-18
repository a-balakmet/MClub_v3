package kz.magnum.app.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.data.room.RoomNames.parentStoryId
import kz.magnum.app.data.room.RoomNames.parentStoryName
import kz.magnum.app.data.room.RoomNames.stories

@Entity(tableName = stories)
data class Story(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = parentStoryId) override val id: Int,
    @ColumnInfo(name = parentStoryName) override val name: String,
    val storyOrder: Int,
    val storyLink: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val preview: ByteArray? = null,
    val isViewedStory: Boolean
): BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Story

        if (id != other.id) return false
        if (name != other.name) return false
        if (storyOrder != other.storyOrder) return false
        if (storyLink != other.storyLink) return false
        if (preview != null) {
            if (other.preview == null) return false
            if (!preview.contentEquals(other.preview)) return false
        } else if (other.preview != null) return false
        if (isViewedStory != other.isViewedStory) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + storyOrder
        result = 31 * result + storyLink.hashCode()
        result = 31 * result + (preview?.contentHashCode() ?: 0)
        result = 31 * result + isViewedStory.hashCode()
        return result
    }

}
