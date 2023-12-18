package kz.magnum.app.data.remote.versions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import aab.lib.commons.extensions.StringConvert.dateTimeTFormatter
import aab.lib.commons.extensions.StringConvert.toDateTime
import kz.magnum.app.data.room.entities.Version

@Serializable
data class VersionDto(
    @SerialName("name") val name: String,
    @SerialName("dt_updated") val date: String,
)

fun VersionDto.toVersion() = Version(id = 0, name = name, date = date.substring(0, 19).toDateTime(dateTimeTFormatter))