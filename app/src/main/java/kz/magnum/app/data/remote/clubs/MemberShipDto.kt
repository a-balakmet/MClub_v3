package kz.magnum.app.data.remote.clubs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemberShipDto(@SerialName("source") val source: String)
