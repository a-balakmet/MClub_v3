package kz.magnum.app.data.remote.stories

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import aab.lib.commons.data.room.BaseEntity

@Serializable
data class StoryDto(
    @SerialName("id") override val id: Int,
    @SerialName("name") override val name: String,
    @SerialName("preview") val preview: String,
    @SerialName("full_screen_stories") val storyScreens: List<StoryScreenDto>,
    @SerialName("sort_order") val sortOrder: Int
): BaseEntity()