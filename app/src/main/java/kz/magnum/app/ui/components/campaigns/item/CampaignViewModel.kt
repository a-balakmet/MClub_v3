package kz.magnum.app.ui.components.campaigns.item

import aab.lib.commons.domain.models.LoadingState
import aab.lib.commons.domain.models.Resource
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.data.remote.campaigns.CampaignDataDto
import kz.magnum.app.data.remote.promotions.StickerExchangeDto
import kz.magnum.app.domain.useCase.itemsUseCase.ItemUseCase
import kz.magnum.app.domain.useCase.postUseCase.StickersExchangeUseCase
import kz.magnum.app.ui.builders.baseViewModels.ItemViewModel

class CampaignViewModel(
    useCase: ItemUseCase<CampaignDataDto>,
    private val stickersExchangeUseCase: StickersExchangeUseCase,
) : ItemViewModel<CampaignDataDto>(useCase) {

    override val savedState: String = "one_campaign"

    private val _exchangeState = mutableStateOf(LoadingState<Unit>())
    val exchangeState: State<LoadingState<Unit>> = _exchangeState

    init {
        onInit()
    }

    fun saveQueryType() = ioDispatcher.async{
        viewModelScope.launch {
            dataStore.putPreference(AppStoreKeys.savedQuery, "campaign")
        }
    }

    fun changeStickers(uuid: String, id: Int) {
        viewModelScope.launch {
            val body = StickerExchangeDto(uuid, id)
            stickersExchangeUseCase.invoke(body, null).collect {
                when(it) {
                    is Resource.Loading -> _exchangeState.value = LoadingState(isLoading = true)
                    is Resource.Success -> _exchangeState.value = LoadingState(result = it.data)
                    is Resource.Error -> _exchangeState.value = LoadingState(error = it)
                    else -> Unit
                }
            }
        }
    }
}