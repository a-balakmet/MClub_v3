package kz.magnum.app.data.remote.directory.shops

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.R
import kz.magnum.app.data.room.entities.ShopProperty

@Serializable
data class ShopPropertyDto(
    @SerialName("id") override var id: Int,
    @SerialName("name") override var name: String,
    @SerialName("label") var label: String,
    @SerialName("type") var type: String
): BaseEntity()

fun ShopPropertyDto.toShopProperty(): ShopProperty {
    val icon = when (name) {
        "bakery" -> R.drawable.bakery
        "pizza" -> R.drawable.pizza
        "pastry" -> R.drawable.pastry
        "chicken" -> R.drawable.chicken
        "cookery" -> R.drawable.cookery
        "toys" -> R.drawable.toys
        "zoo" -> R.drawable.zoo
        "right_food" -> R.drawable.right_food
        "parking_zone" -> R.drawable.parking_zone
        "atm" -> R.drawable.atm
        "self_cash_desk" -> R.drawable.self_cash_desk
        "coffee" -> R.drawable.coffee
        "m_cafe" -> R.drawable.m_cafe
        "pay_terminal" -> R.drawable.pay_terminal
        "paid_parking" -> R.drawable.paid_parking
        else -> R.drawable.mini_icon
    }
    return ShopProperty(id, name, type, label, icon)
}
