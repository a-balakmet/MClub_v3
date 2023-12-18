package kz.magnum.app.data.remote.campaigns

import aab.lib.commons.data.room.BaseEntity
import aab.lib.commons.extensions.StringConvert
import aab.lib.commons.extensions.StringConvert.toDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CampaignDataDto(
    // basics
    @SerialName("id") override val id: Int,
    @SerialName("title") override val name: String,
    @SerialName("short_description") val shortDescription: String,
    @SerialName("full_description") val fullDescription: String?,
    @SerialName("conditions_full") val fullConditions: String?,
    @SerialName("conditions_short") val shortConditions: String?,
    @SerialName("image_url") val landscapeImage: String,
    @SerialName("image_vertical_url") val squareImage: String,
    @SerialName("start") val start: String,
    @SerialName("stop") val stop: String,
    @SerialName("badges") val badges: List<String>?,
    // adds
    @SerialName("download_link") val downloadLink: String?,
    @SerialName("chance_sum") val chancesSum: Double?,
    @SerialName("have_product_lists") val withProducts: Boolean?,
    @SerialName("disclaimer") val disclaimer: String?,
    @SerialName("info_button") val infoButton: CampaignInfoButton?,
    @SerialName("conditions_block") val conditionsBlock: List<CampaignConditions>?,
    @SerialName("achievement_client") val clientAchievement: ClientAchievement?,
) : BaseEntity() {

    fun startDateTime() = start.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter)
    fun stopDateTime() = stop.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter)

}
