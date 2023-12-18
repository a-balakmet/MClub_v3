package kz.magnum.app.data.remote.campaigns

import kotlinx.serialization.Serializable

@Serializable
data class CampaignConditions(
    val conditions: List<String>?,
    val description: String?,
    val title: String?
)
