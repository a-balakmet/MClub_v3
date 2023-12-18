package kz.magnum.app.ui.builders.baseViews

import aab.lib.commons.data.room.BaseEntity
import aab.lib.commons.ui.composableAdds.isScrolledToEnd
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kz.magnum.app.domain.models.ListUIEvent
import kz.magnum.app.ui.builders.baseViewModels.PagedListViewModel
import kz.magnum.app.ui.singleViews.badges.ErrorBadge
import kz.magnum.app.ui.singleViews.badges.NoDataBadge
import kz.magnum.app.ui.theme.Primary

@Composable
fun <T : BaseEntity> PagedListView(
    viewModel: PagedListViewModel<T>,
    loadingView: @Composable () -> Unit = {},
    listItem: @Composable (T) -> Unit = {},
    noDataTitle: String = "no_data",
    noDataMessage: String? = null,
) {

    val loadingState = viewModel.state.value
    val list = viewModel.list

    val isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    val scrollState = rememberLazyListState()
    val endOfListReached by remember {
        derivedStateOf {
            scrollState.isScrolledToEnd()
        }
    }

    LaunchedEffect(endOfListReached) {
        if (viewModel.list.isNotEmpty()) {
            viewModel.onEvent(event = ListUIEvent.LoadNext)
        }
    }

    if (loadingState.isLoading && list.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 0..2) {
                loadingView()
            }
        }
    }
    if (list.isEmpty() && loadingState.result != null) {
        NoDataBadge(titleText = noDataTitle, messageText = noDataMessage)
    } else {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.onEvent(event = ListUIEvent.Refresh(null))
            },
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    scale = true,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    contentColor = Primary
                )
            }) {
            Box(modifier = Modifier.fillMaxWidth()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    state = scrollState,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
                ) {
                    items(list, itemContent = { item ->
                        listItem(item)
                    })
                    if (loadingState.isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                            ) {
                                loadingView()
                            }
                        }
                    }
                    item {
                        Box(modifier = Modifier.height(64.dp))
                    }
                }
            }
        }
    }
    loadingState.error?.let {
        ErrorBadge(error = it)
    }
}