package kz.magnum.app.ui.builders.baseViewModels

import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.data.room.BaseEntity
import aab.lib.commons.domain.models.LoadingState
import aab.lib.commons.domain.models.Resource
import aab.lib.commons.ui.navigation.Navigator
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kz.magnum.app.application.Generic
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.di.DINames
import kz.magnum.app.domain.models.Coupon
import kz.magnum.app.domain.models.ListUIEvent
import kz.magnum.app.domain.useCase.listsUseCase.ListUseCase
import kz.magnum.app.ui.navigation.Destinations
import kz.magnum.app.ui.navigation.NavigationActions
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject

abstract class ListViewModel<T : BaseEntity>(
//    private val handle: SavedStateHandle,
//    private val ioDispatcher: CoroutineScope,
//    private val navigator: Navigator,
//    private val dataStore: DataStoreRepository,
    private val useCase: ListUseCase<T>
) : ViewModel() {

    abstract val savedState: String

    private val handle: SavedStateHandle by inject(SavedStateHandle::class.java, named(DINames.stateHandler))
    private val ioDispatcher: CoroutineScope by inject(CoroutineScope::class.java, named(DINames.ioDispatcher))
    private val navigator: Navigator by inject(Navigator::class.java)
    val dataStore: DataStoreRepository by inject(DataStoreRepository::class.java)

    private val _state = mutableStateOf(LoadingState<List<T>>())
    val state: State<LoadingState<List<T>>> = _state

    fun onInit() {
        getData()
    }

    private fun getData() {
        if (handle.get<Boolean?>(savedState) == null) {
            viewModelScope.launch {
                useCase.invoke().collect {
                    when (it) {
                        is Resource.Loading -> _state.value = LoadingState(isLoading = true)
                        is Resource.Success -> {
                            if (it.data.isEmpty()) {
                                _state.value = LoadingState(result = it.data)
                            } else {
                                val item = it.data.first()
                                if (Generic<Coupon>().checkType(item)) {
                                    val list = it.data.filter { c -> (c as Coupon).stateInt < 3 }
                                    _state.value = LoadingState(result = list)
                                } else {
                                    _state.value = LoadingState(result = it.data)
                                }
                            }
                        }

                        is Resource.Error -> _state.value = LoadingState(error = it)
                        else -> Unit
                    }
                }
                handle[savedState] = true
            }
        }
    }

    fun onEvent(event: ListUIEvent<T>) {
        when (event) {
            is ListUIEvent.Refresh -> {
                handle[savedState] = null
                getData()
            }

            is ListUIEvent.OpenLink -> {
                val context = event.context
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                    context.startActivity(intent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://magnum.kz/"))
                    context.startActivity(intent)
                }
            }

            is ListUIEvent.SelectAndNavigate -> {
                viewModelScope.launch {
                    dataStore.putPreference(AppStoreKeys.savedId, event.item.id)
                }.invokeOnCompletion {
                    if (event.isNavigate) {
                        if (event.childScreen == Destinations.campaignScreen || event.childScreen == Destinations.clubScreen) {
                            navigator.navigate(NavigationActions.SubScreen.toImageBarDetailScreen(event.childScreen), event.bottomIndex)
                        } else {
                            if (event.childScreen == Destinations.couponScreen) {
                                navigator.getController()?.currentBackStackEntry?.savedStateHandle?.set("coupon", event.item)
                            }
                            navigator.navigate(NavigationActions.SubScreen.toCommonDetailScreen(event.childScreen), event.bottomIndex)
                        }
                    }
                }
            }

            is ListUIEvent.NavigateByActions -> {
                navigator.navigate(NavigationActions.SubScreen.toParentScreen(screenName = event.screenName), event.bottomIndex)
            }

            is ListUIEvent.DirectNavigation -> {
                navigator.navigate(event.action, 0)
            }

            else -> Unit
        }
    }

    inline fun <reified T> isCoupon() = when (T::class) {
        Coupon::class -> true
        else -> false
    }

//    fun refresh() {
//        handle[savedState] = null
//        getData()
//    }
//
//    fun saveId(id: Int) = ioDispatcher.async {
//        dataStore.putPreference(AppStoreKeys.savedId, id)
//        return@async true
//    }

    override fun onCleared() {
        viewModelScope.launch {
            dataStore.removePreference(AppStoreKeys.savedId)
            dataStore.removePreference(AppStoreKeys.refresher)
        }
        super.onCleared()
    }
}

