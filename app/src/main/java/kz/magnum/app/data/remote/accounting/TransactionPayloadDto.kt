package kz.magnum.app.data.remote.accounting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionPayloadDto(
    @SerialName("dt_start") val dateStart: String?,
    @SerialName("dt_finish") val dateEnd: String?
)
