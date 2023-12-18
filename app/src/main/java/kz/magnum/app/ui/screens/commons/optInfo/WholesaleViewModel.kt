package kz.magnum.app.ui.screens.commons.optInfo

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
import kz.magnum.app.domain.useCase.postUseCase.IINUseCase
import kz.magnum.app.ui.navigation.NavigationActions

class WholesaleViewModel(
    private val navigator: Navigator,
    private val ioDispatcher: CoroutineScope,
    private val database: MagnumClubDatabase,
    private val dataStore: DataStoreRepository,
    private val iinUseCase: IINUseCase,
    private val useCase: BonusCardBindUseCase
) : ViewModel() {

    var iin = "ИИН"

    private val _exchangeState = mutableStateOf(LoadingState<Unit>())
    val exchangeState: State<LoadingState<Unit>> = _exchangeState

    init {
        ioDispatcher.launch {
            val locale = dataStore.readPreference(AppStoreKeys.locale, "ru")
            if (locale == "kk") {
                iin = "ЖСН"
            }
        }
    }

    fun postIIN(iin: String) {
        viewModelScope.launch{
            iinUseCase.invoke(mapOf("iin" to iin), null).collect {
                when (it) {
                    is Resource.Loading -> _exchangeState.value = LoadingState(isLoading = true)

                    is Resource.Success -> generateCard()

                    is Resource.Error -> _exchangeState.value = LoadingState(error = it)
                    else -> Unit
                }
            }
        }
    }

    private fun generateCard() {
        viewModelScope.launch {
            val body = BonusCardBind(cardNumber = null, typeId = 2)
            useCase.invoke(body, null, BonusCardDto::toBonusCard).collect {
                when (it) {
                    is Resource.Loading -> _exchangeState.value = LoadingState(isLoading = true)

                    is Resource.Success -> {
                        ioDispatcher.launch {
                            database.cardsDao().insert(it.data)
                        }.invokeOnCompletion {
                            _exchangeState.value = LoadingState(isLoading = false)
                            navigator.navigate(NavigationActions.Registration.toCardCreated(), 0)
                        }
                    }

                    is Resource.Error -> _exchangeState.value = LoadingState(error = it)
                    else -> Unit
                }
            }
        }
    }
}