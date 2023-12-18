package kz.magnum.app.ui.screens.loginBlock.cardScanner

import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.ui.navigation.Navigator
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.data.room.MagnumClubDatabase

class CardScannerViewModel(
    private val navigator: Navigator,
    ioDispatcher: CoroutineScope,
    private val dataStore: DataStoreRepository,
    private val database: MagnumClubDatabase
) : ViewModel() {

    var permissionText = "Нет разрешения к камере телефона.\nОткрыть разрешения?"
    var noText = "Нет"
    var scanText = "Поместите штрих-код карты в рамку"

    init {
        ioDispatcher.launch {
            val locale = dataStore.readPreference(key = AppStoreKeys.locale, "ru")
            val permission = database.translationDao().getTranslation("camera_disabled")
            permissionText = when (locale) {
                "kk" -> permission?.kk ?: "Телефон камерасына рұқсат жоқ.\nРұқсаттарды ашу керек пе?"
                else -> permission?.ru ?: "Нет разрешения к камере телефона.\nОткрыть разрешения?"
            }
            noText = when (locale) {
                "kk" -> "Жоқ"
                else -> "Нет"
            }
            val scan = database.translationDao().getTranslation("card_barcode")
            scanText = when (locale) {
                "kk" -> scan?.kk ?: "Картаның штрих-кодын жақтауға салыңыз"
                else -> scan?.ru ?: "Поместите штрих-код карты в рамку"
            }
        }
    }

    fun navigateBack(barcode: String?) {
        navigator.getController()?.let {
            it.popBackStack()
            if (barcode != null) {
                it.currentBackStackEntry?.savedStateHandle?.set("barcode", barcode)
            }
        }
    }
}