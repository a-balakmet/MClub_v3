package kz.magnum.app.ui.screens.commons.createPinCode

import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.ui.navigation.Navigator
import androidx.biometric.BiometricManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.ui.navigation.NavigationActions

class PinCodeCreationViewModel(
    private val navigator: Navigator,
    private val dataStore: DataStoreRepository
): ViewModel() {

    var hasBiometric = false

    init {
        hasBiometric = getBiometric()
    }

    fun navigateNext(pin: String?, biometric: Boolean) {
        val job = viewModelScope.launch {
            dataStore.putPreference(AppStoreKeys.isFingerprint , biometric)
            if (pin != null) {
                dataStore.putPreference(AppStoreKeys.pinCode, pin)
            }
        }
        job.invokeOnCompletion {
            navigator.navigate(NavigationActions.Parents.toMain(), 0)
        }
    }

    private fun getBiometric(): Boolean {
        val biometricManager = BiometricManager.from(dataStore.provideContext())
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE, BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE, BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> false
            else -> false
        }
    }
}