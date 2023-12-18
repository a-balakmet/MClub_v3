package kz.magnum.app.data.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import kz.magnum.app.data.room.RoomNames.parentStoryId
import kz.magnum.app.data.room.entities.Story
import kz.magnum.app.data.room.entities.StoryScreen

data class StoryWithScreens(
    @Embedded val story: Story,
    @Relation(
        entity = StoryScreen::class,
        parentColumn = parentStoryId,
        entityColumn = "story_${parentStoryId}"
    )
    val shops: List<StoryScreen>
)