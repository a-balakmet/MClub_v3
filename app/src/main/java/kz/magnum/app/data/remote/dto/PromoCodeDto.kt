package kz.magnum.app.data.remote.dto

import aab.lib.commons.data.room.BaseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PromoCodeDto (
    @SerialName("id") override val id: Int,
    @SerialName("code") override val name: String,
    @SerialName("client_id") val clientID: Int,
    @SerialName("dt_created") val dateCreated: String,
    @SerialName("promo_code_type_id") val promoCodeTypeID: Long,
    @SerialName("status") val status: Long
): BaseEntity()
