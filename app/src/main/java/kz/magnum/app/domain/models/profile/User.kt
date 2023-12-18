package kz.magnum.app.domain.models.profile

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val birthday: String,
    val cityId: Int,
    val cityName: String,
    val cityLatitude: Double,
    val cityLongitude: Double,
    val email: String,
    val firstName: String,
    val secondName: String,
    val lastName: String,
    val gender: String,
    val phone: String,
    val favoriteShop: Long,
    val carPlate: String?,
    val printReceipt: Boolean?,
    val dateToChangeCarPlate: String?
)
