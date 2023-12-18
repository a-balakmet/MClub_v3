package kz.magnum.app.domain.models.profile

sealed class ProfileEditUIEvent {

    class NamesSetter(val value: String, val type: ProfileItemType, val isName: Boolean?) : ProfileEditUIEvent()
    data object CompletedProfile : ProfileEditUIEvent()
}
