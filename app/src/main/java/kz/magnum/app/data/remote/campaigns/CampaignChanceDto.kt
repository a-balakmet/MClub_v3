package kz.magnum.app.data.remote.campaigns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CampaignChanceDto(
    @SerialName("id") val id: Int?,
    @SerialName("campaign_id") val campaignID: Int,
    @SerialName("campaign_name") val campaignName: String?,
    @SerialName("client_id") val clientID: Int,
    @SerialName("reference") val reference: Int?,
    @SerialName("reference_type") val referenceType: Int?,
    @SerialName("value") val value: Float?
)
