package kz.magnum.app.ui.screens.appBlock.main

import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.domain.models.Resource
import aab.lib.commons.extensions.toDataClass
import aab.lib.commons.ui.navigation.Navigator
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.domain.models.NavigationItem
import kz.magnum.app.domain.models.profile.User
import kz.magnum.app.domain.useCase.itemsUseCase.ItemUseCase
import kz.magnum.app.domain.useCase.TranslationUseCase
import kz.magnum.app.ui.navigation.Destinations.monthProgressScreen
import kz.magnum.app.ui.navigation.NavigationActions

class MainScreenViewModel(
    private val handle: SavedStateHandle,
    private val ioDispatcher: CoroutineScope,
    private val navigator: Navigator,
    private val translationUseCase: TranslationUseCase,
    private val database: MagnumClubDatabase,
    private val dataStore: DataStoreRepository,
    private val deleteUseCase: ItemUseCase<Unit>,
    private val profileProviderUseCase: ItemUseCase<User>
) : ViewModel() {

    private var refresher = MutableStateFlow<Boolean?>(handle["loaded"])
    var greetings = "Привет"
    var currentRoute = mutableStateOf(monthProgressScreen)

    init {
        ioDispatcher.launch {
            val hello = translationUseCase.invoke("hi_there")
            greetings = "$hello!"
            dataStore.getPreference(key = AppStoreKeys.user, "").collect {
                it.toDataClass<User?>().let { user ->
                    user?.let { u ->
                        if (user.firstName != "") greetings = "$hello, ${u.firstName}!"
                    }
                }
            }
        }
        viewModelScope.launch {
            refresher.collect {
                if (it == null) {
                    profileProviderUseCase.invoke(null).collect()
                    handle["loaded"] = true
                }
            }
        }
    }

    fun updateChild(childRoute: String, withNavigation: Boolean) {
        currentRoute.value = childRoute
        if (withNavigation) {
            navigator.navigate(NavigationActions.SubScreen.toCommonDetailScreen(childRoute), 0)
        }
    }

    fun navigateToScreen(item: NavigationItem) {
        navigator.navigate(item.action, 0)
    }

    fun deleteProfile() {
        viewModelScope.launch {
            deleteUseCase.invoke(null).collect {
                when (it) {
                    is Resource.Success -> {
                        dataStore.clearAllPreference()
                        ioDispatcher.launch {
                            database.clearAllTables()
                        }.invokeOnCompletion {
                            navigator.navigate(NavigationActions.Registration.toPhoneInput(false), 0)
                        }
                    }

                    else -> Unit
                }
            }
        }
    }
}