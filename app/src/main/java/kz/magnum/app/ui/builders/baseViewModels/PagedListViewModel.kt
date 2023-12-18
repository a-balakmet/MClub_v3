package kz.magnum.app.ui.builders.baseViewModels

import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.data.room.BaseEntity
import aab.lib.commons.domain.models.LoadingState
import aab.lib.commons.domain.models.QueryModel
import aab.lib.commons.domain.models.Resource
import aab.lib.commons.ui.navigation.Navigator
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.data.remote.commons.PageableDto
import kz.magnum.app.domain.models.ListUIEvent
import kz.magnum.app.domain.useCase.pagesUseCase.PageUseCase
import kz.magnum.app.ui.navigation.Destinations
import kz.magnum.app.ui.navigation.NavigationActions
import org.koin.java.KoinJavaComponent.inject

abstract class PagedListViewModel<T : BaseEntity>(
//    private val navigator: Navigator,
//    private val dataStore: DataStoreRepository,
    private val useCase: PageUseCase<T>
) : ViewModel() {

    private val navigator: Navigator by inject(Navigator::class.java)
    val dataStore: DataStoreRepository by inject(DataStoreRepository::class.java)

    private var job: Job? = null

    private val _state = mutableStateOf(LoadingState<PageableDto<T>>())
    val state: State<LoadingState<PageableDto<T>>> = _state

    private val _list = mutableStateListOf<T>()
    val list: List<T> = _list

    abstract var queriesList: List<QueryModel>?
    private var totalPages = 1
    private var currentPage = 0

    val refresher = MutableStateFlow(false)

    fun onInit() {
        getData()
    }

    private fun getData() {
        if (job == null) {
            currentPage++
            if (currentPage == 1) {
                _state.value = LoadingState(isLoading = true)
            } else {
                _state.value = LoadingState(isLoadingMore = true)
            }
            val queries = ArrayList<QueryModel>()
            queries.add(QueryModel(name = page, value = currentPage))
            queriesList?.let {
                queries.addAll(it)
            }
            viewModelScope.launch {
                val savedId = dataStore.readPreference(AppStoreKeys.savedId, 0)
                if (savedId != 0) {
                    queries.add(QueryModel(name = reference, value = savedId))
                }
                val savedType = dataStore.readPreference(AppStoreKeys.savedQuery, "")
                if (savedType != "") {
                    queries.add(QueryModel(name = type, value = savedType))
                }
                job = useCase.invoke(queries).onEach {
                    when (it) {
                        is Resource.Loading -> _state.value = LoadingState(isLoading = true)
                        is Resource.LoadingMore -> _state.value = LoadingState(isLoadingMore = true)
                        is Resource.Success -> {
                            totalPages = it.data.totalPages
                            _list.addAll(it.data.content)
                            _state.value = LoadingState(result = it.data)
                        }

                        is Resource.Error -> _state.value = LoadingState(error = it)
                        else -> Unit
                    }
                }.launchIn(viewModelScope)
            }.invokeOnCompletion {
                job = null
            }
        }
    }

    fun onEvent(event: ListUIEvent<T>) {
        when (event) {
            is ListUIEvent.Refresh -> {
                _list.clear()
                totalPages = 1
                queriesList = event.queries
                currentPage = 0
                onRefresh()
                getData()
            }

            is ListUIEvent.DropQueries -> {
                _list.clear()
                totalPages = 1
                currentPage = 0
                queriesList = event.defaultQueries
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
                            navigator.navigate(NavigationActions.SubScreen.toCommonDetailScreen(event.childScreen), event.bottomIndex)
                        }
                    }
                }
            }

            is ListUIEvent.LoadNext -> {
                if (currentPage != totalPages) {
                    getData()
                }
            }

            else -> Unit
        }
    }

    abstract fun onRefresh()


    override fun onCleared() {
        viewModelScope.launch {
            //dataStore.removePreference(AppStoreKeys.savedId)
            dataStore.removePreference(AppStoreKeys.savedQuery)
        }
        super.onCleared()
    }

    companion object {
        const val page = "page"
        const val reference = "reference"
        const val type = "type"
    }
}