package kz.magnum.app.ui.screens.loginBlock.createProfile

import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.domain.models.LoadingState
import aab.lib.commons.domain.models.Resource
import aab.lib.commons.ui.navigation.Navigator
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.data.remote.profile.ProfileDto
import kz.magnum.app.data.remote.profile.ProfileUpdateDto
import kz.magnum.app.data.remote.profile.toUser
import kz.magnum.app.domain.models.profile.ProfileEditUIEvent
import kz.magnum.app.domain.models.profile.ProfileEditableItem
import kz.magnum.app.domain.models.profile.ProfileItemType
import kz.magnum.app.domain.useCase.TranslationUseCase
import kz.magnum.app.domain.useCase.postUseCase.ProfileEditUseCase
import kz.magnum.app.ui.navigation.NavigationActions


class ProfileCreationViewModel(
    private val navigator: Navigator,
    ioDispatcher: CoroutineScope,
    private val dataStore: DataStoreRepository,
    private val translationUseCase: TranslationUseCase,
    private val useCase: ProfileEditUseCase
) : ViewModel() {

    val texts: ArrayList<ProfileEditableItem> = ArrayList()
    var locale = "ru"

    private val _profileFlow = MutableStateFlow(ProfileUpdateDto())
    val profileFlow = _profileFlow.asStateFlow()

    private val _exchangeState = mutableStateOf(LoadingState<Unit>())
    val exchangeState: State<LoadingState<Unit>> = _exchangeState

    init {
        ioDispatcher.launch {
            locale = dataStore.readPreference(AppStoreKeys.locale, "ru")
            _profileFlow.value = _profileFlow.value.copy(languageCode = locale)
            titles.forEach {
                texts.add(
                    ProfileEditableItem(
                        parentKey = it.key,
                        title = translationUseCase.invoke(it.key),
                        hint = when (it.value) {
                            ProfileItemType.STRING_TYPE -> translationUseCase.invoke("enter_${it.key}")
                            ProfileItemType.BIRTHDAY_TYPE -> translationUseCase.invoke("dd.mm.yy")
                            else -> it.key
                        },
                        error = if (it.value == ProfileItemType.STRING_TYPE) translationUseCase.invoke("wrong_${it.key}") else it.key,
                        type = it.value
                    )
                )
            }
        }
    }

    fun onEvent(event: ProfileEditUIEvent) {
        when (event) {
            is ProfileEditUIEvent.NamesSetter -> {
                when (event.type) {
                    ProfileItemType.STRING_TYPE -> {
                        when (event.isName) {
                            true -> _profileFlow.value = _profileFlow.value.copy(firstName = event.value)
                            false -> _profileFlow.value = _profileFlow.value.copy(lastName = event.value)
                            else -> _profileFlow.value = _profileFlow.value.copy(email = event.value)
                        }
                    }

                    ProfileItemType.BIRTHDAY_TYPE -> _profileFlow.value = _profileFlow.value.copy(birthday = "${event.value}.000Z")
                    ProfileItemType.SEX_TYPE -> _profileFlow.value = _profileFlow.value.copy(gender = event.value)
                }
            }

            is ProfileEditUIEvent.CompletedProfile -> {
                viewModelScope.launch {
                    useCase.invoke(profileFlow.value, null, ProfileDto::toUser).collect {
                        when (it) {
                            is Resource.Loading -> _exchangeState.value = LoadingState(isLoading = true)
                            is Resource.Success -> {
                                val userData = Json.encodeToString(it.data)
                                dataStore.putPreference(AppStoreKeys.user, userData)
                                _exchangeState.value = LoadingState(isLoading = false)
                                navigator.navigate(NavigationActions.Commons.toPinCodeCreation(0), 0)
                            }
                            is Resource.Error -> _exchangeState.value = LoadingState(error = it)
                            else -> Unit
                        }
                    }
                }
            }
        }
    }

    companion object {
        val titles = mapOf(
            "name" to ProfileItemType.STRING_TYPE,
            "surname" to ProfileItemType.STRING_TYPE,
            "birth_date" to ProfileItemType.BIRTHDAY_TYPE,
            "set_sex" to ProfileItemType.SEX_TYPE,
            "mail" to ProfileItemType.STRING_TYPE,
        )
    }
}