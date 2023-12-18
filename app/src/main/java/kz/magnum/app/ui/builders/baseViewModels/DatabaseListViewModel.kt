package kz.magnum.app.ui.builders.baseViewModels

import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.data.room.BaseEntity
import aab.lib.commons.ui.navigation.Navigator
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.di.DINames
import kz.magnum.app.domain.models.ListUIEvent
import kz.magnum.app.domain.useCase.databaseListsUseCase.DatabaseListUseCase
import kz.magnum.app.ui.navigation.Destinations.campaignScreen
import kz.magnum.app.ui.navigation.Destinations.clubScreen
import kz.magnum.app.ui.navigation.NavigationActions
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject

abstract class DatabaseListViewModel<T : BaseEntity>(
    private val handle: SavedStateHandle,
//    private val ioDispatcher: CoroutineScope,
//    private val navigator: Navigator,
//    private val database: MagnumClubDatabase,
//    private val dataStore: DataStoreRepository,
    private val useCase: DatabaseListUseCase<T>
) : ViewModel() {

    abstract val forceLoad: Boolean
    abstract val savedState: String

    abstract var dbFlowProvider: Flow<List<T>>

//    private val handle: SavedStateHandle by inject(SavedStateHandle::class.java, named(DINames.stateHandler))
    private val ioDispatcher: CoroutineScope by inject(CoroutineScope::class.java, named(DINames.ioDispatcher))
    private val navigator: Navigator by inject(Navigator::class.java)
    val database: MagnumClubDatabase by inject(MagnumClubDatabase::class.java)
    private val dataStore: DataStoreRepository by inject(DataStoreRepository::class.java)

    var list = MutableStateFlow<List<T>>(emptyList())

    fun onInit() {
        getData()
    }

    private fun getData() {
        ioDispatcher.launch {
            dbFlowProvider.collect {
                list.value = it
                if (forceLoad) {
                    viewModelScope.launch {
                        useCase.invoke()
                    }
                } else {
                    if (it.isEmpty()) {
                        viewModelScope.launch {
                            useCase.invoke()
                        }
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
                        if (event.childScreen == campaignScreen || event.childScreen == clubScreen) {
                            navigator.navigate(NavigationActions.SubScreen.toImageBarDetailScreen(event.childScreen), event.bottomIndex)
                        } else {
                            navigator.navigate(NavigationActions.SubScreen.toCommonDetailScreen(event.childScreen), event.bottomIndex)
                        }
                    }
                }
            }
            else -> Unit
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            dataStore.removePreference(AppStoreKeys.savedId)
        }
        super.onCleared()
    }
}