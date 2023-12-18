package kz.magnum.app.data.remote.commons

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageableDto<out T>(
    @SerialName("content")
    val content: List<T>,
    @SerialName("page")
    val page: Int,
    @SerialName("size")
    val size: Int,
    @SerialName("totalElements")
    val totalElements: Int,
    @SerialName("totalPages")
    val totalPages: Int,
)


fun <T, E> PageableDto<T>.pageMap(mapper: (T) -> E): PageableDto<E> {
    return PageableDto(content = content.map(mapper), page, size, totalElements, totalPages)
}
