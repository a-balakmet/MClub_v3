package kz.magnum.app.data.remote.accounting

import aab.lib.commons.data.room.BaseEntity
import aab.lib.commons.extensions.StringConvert
import aab.lib.commons.extensions.StringConvert.toDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.magnum.app.R
import kz.magnum.app.domain.models.transactions.Transaction
import java.text.DecimalFormat

@Serializable
data class TransactionDto(
    @SerialName("id") override val id: Int,
    @SerialName("description") override val name: String,
    @SerialName("actor_id") val shopId: Int,
    @SerialName("card_id") val cardId: Long,
    @SerialName("check_amount") val checkAmount: Int,
    @SerialName("accrued_bonuses") val accruedBonuses: Int,
    @SerialName("withdrawal_bonuses") val withdrawalBonuses: Int,
    @SerialName("type") val type: String,
    @SerialName("payload") val payload: TransactionPayloadDto?,
    @SerialName("dt_created") val dateCreated: String,
    @SerialName("reference") val reference: String,
    @SerialName("receipt_link") val receiptLink: String?
) : BaseEntity()

fun TransactionDto.toTransaction(): Transaction {

    fun setName(): String = when (type) {
        "parking" -> "parking_refund"
        "manual" -> "support"
        "partner" -> "partner_bonuses"
        "burn" -> "burning_bonus"
        "referrer" -> "referrer"
        "referee" -> "referee"
        else -> "checkAmountLong"
    }

    fun setIcon(): Int = when (type) {
        "parking" -> R.drawable.parking
        "manual" -> R.drawable.support
        "partner" -> R.drawable.stars
        "burn" -> R.drawable.burning
        "referrer" -> R.drawable.promotion
        "referee" -> R.drawable.promotion
        else -> R.drawable.mini_icon
    }

    return Transaction(
        id = id,
        name = setName(),
        icon = setIcon(),
        sum = if (checkAmount != 0) "${DecimalFormat().format(checkAmount).replace(",", " ")} ₸" else "",
        bonus = if (accruedBonuses != 0) "+ ${DecimalFormat().format(accruedBonuses).replace(",", " ")}" else "",
        withdrawal = if (withdrawalBonuses != 0) DecimalFormat().format(withdrawalBonuses).replace("-", "- ").replace(",", " ") else "",
        dateTime = dateCreated.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter),
        receiptLink = receiptLink
    )
}