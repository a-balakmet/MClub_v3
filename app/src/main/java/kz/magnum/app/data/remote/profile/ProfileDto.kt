package kz.magnum.app.data.remote.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.magnum.app.data.remote.directory.CityDto
import kz.magnum.app.domain.models.profile.User

@Serializable
data class ProfileDto(
    val id: Long,
    val birthday: String?,
    val city: CityDto?,
    @SerialName("dt_created")
    val dateCreated: String,
    @SerialName("dt_vehicle_update_available")
    val dateVehicleUpdateAvailable: String?,
    val email: String?,
    @SerialName("favorite_actor")
    val favoriteShop: Long?,
    @SerialName("first_name")
    val firstName: String?,
    val gender: String?,
    @SerialName("language_code")
    val languageCode: String?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("merchant_id")
    val merchantID: Long?,
    @SerialName("partner_name")
    val partnerName: String?,
    val patronymic: String?,
    val phone: String,
    @SerialName("plate_number")
    val carPlate: String?,
    @SerialName("print_receipt")
    val printReceipt: Boolean?
)

fun ProfileDto.toUser() = User(
    id = id,
    birthday = birthday ?: "",
    cityId = city?.id ?: 1,
    cityName = city?.name ?: "Алматы",
    cityLatitude = city?.geo?.latitude ?: 43.206191,
    cityLongitude = city?.geo?.longitude ?: 76.897995,
    email = email ?: "",
    firstName = firstName ?: "",
    secondName = patronymic ?: "",
    lastName = lastName ?: "",
    gender = gender ?: "male",
    phone = phone,
    favoriteShop = favoriteShop ?: 0,
    carPlate = carPlate ?: "",
    printReceipt = printReceipt,
    dateToChangeCarPlate = dateVehicleUpdateAvailable
)