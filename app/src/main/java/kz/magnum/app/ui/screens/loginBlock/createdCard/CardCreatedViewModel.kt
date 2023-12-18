package kz.magnum.app.ui.screens.loginBlock.createdCard

import aab.lib.commons.ui.navigation.Navigator
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kz.magnum.app.application.BarcodeGenerator
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.data.room.entities.BonusCard
import kz.magnum.app.ui.navigation.NavigationActions

class CardCreatedViewModel(
    private val database: MagnumClubDatabase,
    private val navigator: Navigator
) : ViewModel() {

    val card = MutableStateFlow<BonusCard?>(null)
    var barcodeImage: Bitmap? = null

    init {
        viewModelScope.launch {
            card.value = database.cardsDao().getPreferredCard(1)
            card.value?.let {
                barcodeImage = BarcodeGenerator.generateBonusCardBarcode(it.barcode, 48)
            }
        }
    }

    fun navigateNext(fillProfile: Boolean) {
        if (fillProfile) {
            navigator.navigate(NavigationActions.Registration.toProfileCreation(), 0)
        } else {
            navigator.navigate(NavigationActions.Commons.toPinCodeCreation(0), 0)
        }
    }
}