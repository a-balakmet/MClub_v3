package kz.magnum.app.ui.activity

import aab.lib.commons.data.dataStore.DataStoreKeys
import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.data.preferences.PreferenceRepository
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.domain.useCase.VersionsUpdaterUseCase


class MainActivityViewModel(
    private val handle: SavedStateHandle,
    private val dataStore: DataStoreRepository,
    private val prefs: PreferenceRepository,
    private val versionsUpdater: VersionsUpdaterUseCase,
) : ViewModel() {


    fun onInit() {
        if (prefs.isValueExists(phone)) {
            runMigration()
        } else {
            runLoading()
        }
    }

    private fun runMigration() {
        val job = viewModelScope.launch {
            dataStore.putPreference(AppStoreKeys.locale, prefs.getStringValue(locale))
            dataStore.putPreference(AppStoreKeys.phone, prefs.getStringValue(phone))
            dataStore.putPreference(AppStoreKeys.user, prefs.getStringValue(user))
            dataStore.putPreference(DataStoreKeys.accessToken, prefs.getStringValue(accessToken))
            dataStore.putPreference(DataStoreKeys.refreshToken, prefs.getStringValue(refreshToken))
            dataStore.putPreference(DataStoreKeys.user, prefs.getStringValue(user))
            dataStore.putPreference(AppStoreKeys.pinCode, prefs.getStringValue(pinCode))
            dataStore.putPreference(AppStoreKeys.isFingerprint, prefs.getBooleanValue(isFingerprint))
            dataStore.putPreference(AppStoreKeys.isFaceId, prefs.getBooleanValue(isFaceId))
            dataStore.putPreference(AppStoreKeys.isShowHello, prefs.getBooleanValue(isShowHello))
        }
        job.invokeOnCompletion {
            runLoading()
        }
    }

    fun runLoading(dropSavedState: Boolean = false, localeChanged: Boolean = false) {
        if (dropSavedState || localeChanged) {
            handle["migration"] = null
        }
        if (handle.get<Boolean>("migration") == null) {
            viewModelScope.launch {
                versionsUpdater.getVersions(dropSavedState, localeChanged)
                handle["migration"] = true
            }
        }
    }


    companion object {
        const val locale = "flutter.locale"
        const val phone = "flutter.phone"
        const val accessToken = "flutter.accessToken"
        const val refreshToken = "flutter.refreshToken"
        const val user = "flutter.userProfile"
        const val pinCode = "flutter.pinCode"
        const val isFingerprint = "flutter.isFingerprint"
        const val isFaceId = "flutter.isFaceId"
        const val isShowHello = "flutter.isShowHello"
    }
}

