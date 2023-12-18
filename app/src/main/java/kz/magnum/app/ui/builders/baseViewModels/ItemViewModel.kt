package kz.magnum.app.ui.builders.baseViewModels

import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.domain.models.LoadingState
import aab.lib.commons.domain.models.Resource
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.di.DINames
import kz.magnum.app.domain.useCase.itemsUseCase.ItemUseCase
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent

abstract class ItemViewModel<T>(
//    private val handle: SavedStateHandle,
//    private val ioDispatcher: CoroutineScope,
//    private val dataStore: DataStoreRepository,
    private val useCase: ItemUseCase<T>
): ViewModel() {

    abstract val savedState: String

    val handle: SavedStateHandle by KoinJavaComponent.inject(SavedStateHandle::class.java, named(DINames.stateHandler))
    val ioDispatcher: CoroutineScope by KoinJavaComponent.inject(CoroutineScope::class.java, named(DINames.ioDispatcher))
//    private val navigator: Navigator by KoinJavaComponent.inject(Navigator::class.java)
//    val database: MagnumClubDatabase by KoinJavaComponent.inject(MagnumClubDatabase::class.java)
    val dataStore: DataStoreRepository by KoinJavaComponent.inject(DataStoreRepository::class.java)

    val _state = mutableStateOf(LoadingState<T>())
    val state: State<LoadingState<T>> = _state

    var locale = "ru"

    fun onInit() {
        viewModelScope.launch {
            locale = dataStore.readPreference(AppStoreKeys.locale, "ru")
        }
        getData()
    }

    private fun getData() {
        if (handle.get<Boolean?>(savedState) == null) {
            viewModelScope.launch {
                dataStore.getPreference(AppStoreKeys.savedId, 0).collect { savedId ->
                    useCase.invoke(if (savedId == 0) null else savedId).collect {
                        when (it) {
                            is Resource.Loading -> _state.value = LoadingState(isLoading = true)
                            is Resource.Success -> _state.value = LoadingState(result = it.data)
                            is Resource.Error -> _state.value = LoadingState(error = it)
                            else -> Unit
                        }
                    }
                }
            }.invokeOnCompletion {
                handle[savedState] = true
            }
        }
    }

    fun refresh() {
        handle[savedState] = null
        getData()
    }

    override fun onCleared() {
        viewModelScope.launch {
            dataStore.removePreference(AppStoreKeys.savedId)
        }
        super.onCleared()
    }
}