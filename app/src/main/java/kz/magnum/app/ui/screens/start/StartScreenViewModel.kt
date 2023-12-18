package kz.magnum.app.ui.screens.start

import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.ui.navigation.NavigationAction
import aab.lib.commons.ui.navigation.Navigator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.ui.navigation.NavigationActions

class StartScreenViewModel(
    private val dataStore: DataStoreRepository,
    private val database: MagnumClubDatabase,
    private val navigator: Navigator
) : ViewModel() {

    private val navAction = MutableStateFlow<NavigationAction?>(null)

    init {
        viewModelScope.launch {
            val cards = database.cardsDao().getAll()
            if (cards.isEmpty()) {
                navAction.value = NavigationActions.Registration.toGeoNotification()
            } else {
                if (dataStore.readPreference(key = AppStoreKeys.isShowHello, defaultValue = false)) {
                    navAction.value = NavigationActions.Start.toGreetings()
                } else {
                    val pinCode = dataStore.readPreference(key = AppStoreKeys.pinCode,  defaultValue = "")
                    if (pinCode != "") {
                        navAction.value = NavigationActions.Start.toPinCode()
                    } else {
                        navAction.value = NavigationActions.Parents.toMain()
                    }
                }
            }
            navAction.collect {
                it?.let {
                    navigator.navigate(navigationAction = it, bottomIndex = 1)
                }
            }
        }
    }
}