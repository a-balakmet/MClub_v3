package kz.magnum.app.data.remote.accounting

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import aab.lib.commons.extensions.StringConvert
import aab.lib.commons.extensions.StringConvert.toDateTime
import kz.magnum.app.application.extenions.toChunked
import kz.magnum.app.data.room.entities.BonusCard
import java.text.NumberFormat

@Serializable
data class BonusCardDto (
    val balance: CardBalanceDto,
    @SerialName("card_number")
    val cardNumber: Long,
    val cvs: String,
//    @SerialName("dt_created")
//    val dtCreated: String,
    @SerialName("dt_expired")
    val dateExpired: String,
    @SerialName("dt_registered")
    val dateRegistered: String?,
    val id: Long,
    @SerialName("is_virtual")
    val isVirtual: Boolean,
    val status: String,
    val type: CardTypeDto?
)

fun BonusCardDto.toBonusCard() = BonusCard(
    id = id.toInt(),
    name = ("$cardNumber***").toChunked(4),
    cardNumberNum = cardNumber,
    dateCreated = dateRegistered?.substring(0, 19)?.toDateTime(StringConvert.dateTimeFormatter),
    balance = NumberFormat.getNumberInstance(java.util.Locale.US).format(balance.bonuses).replace(",", " "),
    barcode = StringBuilder().append(cardNumber).append(cvs).toString(),
    type = type?.id ?: 1,
    isActive = status == "active",
    status = if (status == "active") 0 else 1,
    order = type?.id ?: 1
)