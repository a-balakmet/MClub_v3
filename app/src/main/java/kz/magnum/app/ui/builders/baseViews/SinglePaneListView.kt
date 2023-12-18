package kz.magnum.app.ui.builders.baseViews

import aab.lib.commons.data.room.BaseEntity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kz.magnum.app.domain.models.ListUIEvent
import kz.magnum.app.ui.builders.baseViewModels.ListViewModel
import kz.magnum.app.ui.singleViews.badges.NoDataBadge
import kz.magnum.app.ui.singleViews.texts.ErrorText
import kz.magnum.app.ui.theme.Primary

@Composable
fun <T : BaseEntity> SinglePaneListView(
    viewModel: ListViewModel<T>,
    loading: @Composable () -> Unit = {},
    topView: @Composable () -> Unit = {},
    listItem: @Composable (T) -> Unit = {},
    noDataTitle: String = "no_data",
    noDataMessage: String?
) {
    val loadingState = viewModel.state.value

    val isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    if (loadingState.isLoading) {
        Column {
            topView()
            loading()
        }
    } else if (loadingState.result != null) {
        Column {
            topView()

            if (loadingState.result!!.isEmpty()) {
                NoDataBadge(titleText = noDataTitle, messageText = noDataMessage)
            } else {
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = { viewModel.onEvent(event = ListUIEvent.Refresh(null)) },
                    indicator = { state, trigger ->
                        SwipeRefreshIndicator(
                            state = state,
                            refreshTriggerDistance = trigger,
                            scale = true,
                            backgroundColor = MaterialTheme.colorScheme.background,
                            contentColor = Primary
                        )
                    }) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
                    ) {
                        items(loadingState.result!!, itemContent = { item ->
                            listItem(item)
                        })
                    }
                }
            }
        }
    } else if (loadingState.error != null) {
        ErrorText(error = loadingState.error!!)
    } else {
        Box(modifier = Modifier)
    }
}