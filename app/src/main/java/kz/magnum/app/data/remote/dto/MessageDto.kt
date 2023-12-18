package kz.magnum.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.magnum.app.domain.models.Message
import kotlin.random.Random


@Serializable
data class MessageDto(
    @SerialName("dt_created") val dateCreated: String,
    @SerialName("message") val message: String,
    @SerialName("payload") val payload: MessagePayloadDto,
    @SerialName("title") val title: String
)

@Serializable
data class MessagePayloadDto(
    @SerialName("android_link") val androidLink: String?,
    @SerialName("ios_link") val iosLink: String?,
    @SerialName("img_link") val imageLink: String?,
    @SerialName("button") val button: String?,
)

fun MessageDto.toMessage() = Message(
    id = Random.nextInt(),
    name = title,
    message = message,
    dateCreated = dateCreated,
    androidLink = payload.androidLink,
    imageLink = payload.imageLink,
    button = payload.button
)

