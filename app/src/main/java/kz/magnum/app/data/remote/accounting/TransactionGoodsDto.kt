package kz.magnum.app.data.remote.accounting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.magnum.app.domain.models.transactions.TransactionGoods
import java.text.DecimalFormat

@Serializable
data class TransactionGoodsDto(
    @SerialName("name") val name: String,
    @SerialName("quantity") val quantity: Int,
    @SerialName("reference") val reference: String,
    @SerialName("total_price") val totalPrice: Int
)

fun TransactionGoodsDto.toTransactionGoods(): TransactionGoods {

    fun getPrice() = when(quantity) {
            0 -> totalPrice
            else -> totalPrice / quantity
    }

    return TransactionGoods(
        name = name,
        quantityAndPrice = "${getPrice()} x $quantity",
        totalPrice = "${DecimalFormat().format(totalPrice).replace(",", " ")} ₸"
    )
}
