package kz.magnum.app.data.remote.accounting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.magnum.app.domain.models.Burning

@Serializable
data class BurningDto(
    @SerialName("date_burn") val burningDate: String,
    @SerialName("remaining_amount_sum") val remainingBonus: Int,
)

fun BurningDto.toBurning() = Burning(id = remainingBonus, name = burningDate)
