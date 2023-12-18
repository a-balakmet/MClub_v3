package kz.magnum.app.data.remote.accounting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.magnum.app.data.remote.campaigns.CampaignChanceDto
import kz.magnum.app.data.remote.directory.shops.ShopDto
import kz.magnum.app.data.remote.directory.shops.toShop
import kz.magnum.app.domain.models.transactions.TransactionContent

@Serializable
data class TransactionFullDataDto(
    @SerialName("actor") val shop: ShopDto?,
    @SerialName("transaction") val transaction: TransactionDto,
    @SerialName("transaction_items") val transactionItems: List<TransactionGoodsDto>?,
    @SerialName("chances") val chances: List<CampaignChanceDto>?
)

fun TransactionFullDataDto.toTransactionContent(): TransactionContent {

    return TransactionContent(
        id = transaction.id,
        name = "",
        shop = shop?.toShop(null),
        transaction = transaction.toTransaction(),
        goods = transactionItems?.map { it.toTransactionGoods() }
    )
}