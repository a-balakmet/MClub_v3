package kz.magnum.app.ui.components.clubs.item

import aab.lib.commons.domain.models.LoadingState
import aab.lib.commons.domain.models.Resource
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.domain.useCase.itemsUseCase.ItemUseCase
import kz.magnum.app.domain.useCase.postUseCase.EnterClubUseCase
import kz.magnum.app.ui.builders.baseViewModels.ItemViewModel

class ClubViewModel(
    useCase: ItemUseCase<Club>,
    private val exitClubUseCase: ItemUseCase<Unit>,
    private val enterClubUseCase: EnterClubUseCase,
    private val database: MagnumClubDatabase
) : ItemViewModel<Club>(useCase) {

    override val savedState: String = "one_club"

    private val _exchangeState = mutableStateOf(LoadingState<Unit>())
    val exchangeState: State<LoadingState<Unit>> = _exchangeState

    init {
        onInit()
    }

    fun setMembership(club: Club) {
        if (_state.value.result != null) {
            viewModelScope.launch {
                val resource = if (club.isMember) exitClubUseCase.invoke(club.id) else enterClubUseCase.invoke(mapOf("source" to "mobile"), club.id)
                resource.collect {
                    when (it) {
                        is Resource.Loading -> _exchangeState.value = LoadingState(isLoading = true)
                        is Resource.Success -> {
                            ioDispatcher.launch {
                                val dao = database.clubsDao()
                                val clubs = dao.getItemsByName(_state.value.result!!.name)
                                val selectedClub = clubs.firstOrNull()
                                selectedClub?.let { c ->
                                    val newClub = Club(
                                        id = c.id,
                                        name = c.name,
                                        isAdult = c.isAdult,
                                        isMember = !club.isMember,
                                        reference = c.reference,
                                        description = c.description,
                                        documentUrl = c.documentUrl,
                                        externalUrl = c.externalUrl,
                                        merchantId = c.merchantId,
                                        thumbnailUrl = c.thumbnailUrl,
                                        imageUrl = c.imageUrl
                                    )
                                    dao.delete(c)
                                    dao.insert(newClub)
                                    _state.value = LoadingState(result = newClub)
                                }
                            }
                            _exchangeState.value = LoadingState(result = it.data)
                        }

                        is Resource.Error -> _exchangeState.value = LoadingState(error = it)
                        else -> Unit
                    }
                }
            }
        }
    }
}