package kz.magnum.app.data.remote.accounting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardBalanceDto (
    @SerialName("accrued_blocked_bonuses")
    val accruedBlockedBonuses: Long,
    val bonuses: Long,
    @SerialName("withdrawal_blocked_bonuses")
    val withdrawalBlockedBonuses: Long
)
