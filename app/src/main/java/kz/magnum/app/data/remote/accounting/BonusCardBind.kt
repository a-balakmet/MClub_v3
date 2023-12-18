package kz.magnum.app.data.remote.accounting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BonusCardBind(
    @SerialName("card_number") val cardNumber: Long?,
    @SerialName("type_id") val typeId: Int?
)
