package kz.magnum.app.data.remote.directory.shops

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.R
import kz.magnum.app.data.room.entities.ShopType

@Serializable
data class ShopTypeDto(
    @SerialName("Id") override var id: Int,
    @SerialName("Name") override var name: String,
): BaseEntity()

fun ShopTypeDto.toShopType(): ShopType {
    val icon = when (name) {
        "Express" -> R.drawable.m_express
        "Daily" -> R.drawable.m_daily
        "Super" -> R.drawable.m_super
        else -> R.drawable.mini_icon
    }
    return ShopType(id, name, icon)
}
