package kz.magnum.app.ui.screens.loginBlock.createCard

import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.domain.models.LoadingState
import aab.lib.commons.domain.models.Resource
import aab.lib.commons.ui.navigation.Navigator
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.data.remote.accounting.BonusCardBind
import kz.magnum.app.data.remote.accounting.BonusCardDto
import kz.magnum.app.data.remote.accounting.toBonusCard
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.domain.useCase.postUseCase.BonusCardBindUseCase
import kz.magnum.app.ui.navigation.NavigationActions

class CardCreationViewModel(
    val navigator: Navigator,
    private val ioDispatcher: CoroutineScope,
    private val database: MagnumClubDatabase,
    private val dataStore: DataStoreRepository,
    private val useCase: BonusCardBindUseCase
) : ViewModel() {

    var cardHint = "Номер карты"

    private val _exchangeState = mutableStateOf(LoadingState<Unit>())
    val exchangeState: State<LoadingState<Unit>> = _exchangeState

    init {
        ioDispatcher.launch {
            val hint = database.translationDao().getTranslation("card_number")
            cardHint = when (dataStore.readPreference(key = AppStoreKeys.locale, defaultValue = "")) {
                "kk" -> hint?.kk ?: "Карта нөмірі"
                else -> hint?.ru ?: "Номер карты"
            }
        }
    }

    fun bindCard(cardBarcode: String?) {
        viewModelScope.launch {
            val cardNumber: Long? = cardBarcode?.toLong()
            val body = BonusCardBind(cardNumber = cardNumber, typeId = if (cardNumber == null) 1 else null)
            useCase.invoke(body, null, BonusCardDto::toBonusCard).collect {
                when (it) {
                    is Resource.Loading -> _exchangeState.value = LoadingState(isLoading = true)

                    is Resource.Success -> {
                        ioDispatcher.launch {
                            database.cardsDao().insert(it.data)
                        }.invokeOnCompletion {
                            _exchangeState.value = LoadingState(isLoading = false)
                            navigateNext(true)
                        }
                    }

                    is Resource.Error -> _exchangeState.value = LoadingState(error = it)
                    else -> Unit
                }
            }
        }
    }

    fun navigateNext(cardCreated: Boolean) {
        if (cardCreated) {
            navigator.navigate(NavigationActions.Registration.toCardCreated(), 0)
        } else {
            navigator.navigate(NavigationActions.Commons.toWholesaleInfo(), 0)
        }
    }
}