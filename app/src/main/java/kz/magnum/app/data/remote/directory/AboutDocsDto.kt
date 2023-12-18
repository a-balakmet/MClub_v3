package kz.magnum.app.data.remote.directory

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.magnum.app.BuildConfig
import kz.magnum.app.domain.models.AboutDoc
import kotlin.random.Random

@Serializable
data class AboutDocsDto(
    @SerialName("link") val link: String,
    @SerialName("name") val name: String
)

fun AboutDocsDto.toAboutDoc() = AboutDoc(
    id = Random.nextInt(),
    name = name,
    link = link.replace("/var", "${BuildConfig.BASE_URL}storage").replace("/client-api", "")
)
