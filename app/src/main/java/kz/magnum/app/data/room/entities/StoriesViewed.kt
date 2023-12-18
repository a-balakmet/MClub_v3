package kz.magnum.app.data.room.entities

import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.magnum.app.data.room.RoomNames.viewsStories

@Entity(
    tableName = viewsStories,
    primaryKeys = ["parentStoryId", "childStoryScreen"]
    )
@Serializable
data class StoriesViewed(
    @SerialName("stories_id") val parentStoryId: Int,
    @SerialName("full_screen_stories_id") val childStoryScreen: Int,
    @SerialName("dt_start") val dateStart: String,
    @SerialName("dt_end") val dateEnd: String,
    val uploaded: Boolean
)

