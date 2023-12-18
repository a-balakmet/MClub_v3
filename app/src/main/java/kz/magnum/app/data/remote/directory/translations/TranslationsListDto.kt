package kz.magnum.app.data.remote.directory.translations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslationsListDto(
    @SerialName("translation_list")
    val translations: List<TranslationsDto>,
    @SerialName("version")
    val version: Int
)

fun TranslationsListDto.toTranslationList() = translations.map { it.toTranslation() }