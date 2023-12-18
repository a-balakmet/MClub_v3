package kz.magnum.app.data.remote.accounting

import kotlinx.serialization.Serializable

@Serializable
data class CardTypeDto (
    val id: Int,
    val name: String
)
