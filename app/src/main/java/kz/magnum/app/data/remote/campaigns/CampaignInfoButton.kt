package kz.magnum.app.data.remote.campaigns

import kotlinx.serialization.Serializable

@Serializable
data class CampaignInfoButton(
    val link: String?,
    val title: String?
)
