package kz.magnum.app.ui.screens.pinCode

import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.ui.navigation.Navigator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.domain.useCase.TranslationUseCase
import kz.magnum.app.ui.navigation.NavigationActions

class PinCodeViewModel(
    ioDispatcher: CoroutineScope,
    private val navigator: Navigator,
    private val dataStore: DataStoreRepository,
    private val translationUseCase: TranslationUseCase
): ViewModel() {

    var pincode = ""
    var isFinger = false
    var fingerText = "Приложите палец к сканеру"
    var usePinText = "Использовать ПИН-код"

    init {
        ioDispatcher.launch {
            fingerText = translationUseCase.invoke("scan_fingerprint")
            usePinText = translationUseCase.invoke("use_pin")
            pincode = dataStore.readPreference(AppStoreKeys.pinCode, "")
            isFinger = dataStore.readPreference(AppStoreKeys.isFingerprint, false)
        }
    }

    fun navigateNext(correctCode: Boolean) {
        if (correctCode) {
            navigator.navigate(NavigationActions.Parents.toMain(), 0)
        } else {
            val job = viewModelScope.launch {
                dataStore.clearAllPreference()
            }
            job.invokeOnCompletion {
                navigator.navigate(NavigationActions.Registration.toPhoneInput(true), 0)
            }
        }
    }
}