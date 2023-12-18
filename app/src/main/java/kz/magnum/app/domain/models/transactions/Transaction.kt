package kz.magnum.app.domain.models.transactions

import aab.lib.commons.data.room.BaseEntity
import java.time.LocalDateTime

data class Transaction(
    override val id: Int,
    override val name: String,
    val icon: Int,
    val sum: String,
    val bonus: String,
    val withdrawal: String,
    val dateTime: LocalDateTime,
    val receiptLink: String?
):BaseEntity()
