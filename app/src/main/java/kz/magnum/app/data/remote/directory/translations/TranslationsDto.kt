package kz.magnum.app.data.remote.directory.translations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.magnum.app.data.room.entities.Translation

@Serializable
data class TranslationsDto(
    @SerialName("name")
    val key: String,
    @SerialName("value_ml")
    val values: TranslationDto,
)

fun TranslationsDto.toTranslation() = Translation(
    id = 0,
    name = key,
    ru = values.ru.replace("\\n", "\n"),
    kk = values.kk.replace("\\n", "\n"),
    en = values.en.replace("\\n", "\n")
)