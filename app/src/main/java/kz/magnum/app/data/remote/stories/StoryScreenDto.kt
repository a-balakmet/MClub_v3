package kz.magnum.app.data.remote.stories

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.data.commonModels.StoryAction

@Serializable
data class StoryScreenDto(
    @SerialName("id") override val id: Int,
    @SerialName("image") override val name: String,
    @SerialName("actions")val actions: List<StoryAction>?,
    @SerialName("sort_order") val sortOrder: Int?
): BaseEntity()
