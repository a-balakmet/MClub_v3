package kz.magnum.app.data.commonModels

import kotlinx.serialization.Serializable

@Serializable
data class StoryAction(
    val button: String,
    val styles: String,
    val type: String,
    val value: String
)
