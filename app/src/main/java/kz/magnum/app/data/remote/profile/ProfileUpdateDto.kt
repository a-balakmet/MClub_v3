package kz.magnum.app.data.remote.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileUpdateDto (
    @SerialName("birthday") var birthday: String = "",
    @SerialName("city_id") var cityID: Int = 1,
    @SerialName("email") var email: String = "",
    @SerialName("favorite_actor") var favoriteShop: Int = 0,
    @SerialName("first_name") var firstName: String = "",
    @SerialName("last_name") var lastName: String = "",
    @SerialName("gender") var gender: String = "male",
    @SerialName("language_code") var languageCode: String = "ru",
    @SerialName("print_receipt") var printReceipt: Boolean = true
)
