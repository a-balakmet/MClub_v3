package kz.magnum.app.data.remote.directory.translations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslationDto(
    @SerialName("en")
    val en: String,
    @SerialName("kk")
    val kk: String,
    @SerialName("ru")
    val ru: String,
)