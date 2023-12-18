package kz.magnum.app.data.remote.directory

import aab.lib.commons.data.room.BaseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PartnerProductDto(
    @SerialName("id") override val id: Int,
    @SerialName("name") override val name: String,
    @SerialName("bar_code") val barCode: String,
    @SerialName("reference") val reference: String
): BaseEntity()
