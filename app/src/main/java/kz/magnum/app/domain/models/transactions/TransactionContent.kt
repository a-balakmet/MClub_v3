package kz.magnum.app.domain.models.transactions

import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.data.room.entities.Shop

data class TransactionContent(
    override val id: Int,
    override val name: String,
    val shop: Shop?,
    val transaction: Transaction,
    val goods: List<TransactionGoods>?
): BaseEntity()
