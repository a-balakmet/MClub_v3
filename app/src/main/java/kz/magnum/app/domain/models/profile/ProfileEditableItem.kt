package kz.magnum.app.domain.models.profile

data class ProfileEditableItem(
    val parentKey: String,
    val title: String,
    val hint: String,
    val error: String,
    val type: ProfileItemType
)

enum class ProfileItemType {
    STRING_TYPE, BIRTHDAY_TYPE, SEX_TYPE
}