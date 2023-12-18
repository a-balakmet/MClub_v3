package kz.magnum.app.ui.builders.baseViews

import aab.lib.commons.data.room.BaseEntity
import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import kz.magnum.app.ui.singleViews.badges.ErrorBadge
import kz.magnum.app.ui.theme.Primary

@Composable
fun <T : BaseEntity> TwoPanesListView(
    viewModel: ListViewModel<T>,
    loading: @Composable () -> Unit = {},
    topView: @Composable () -> Unit = {},
    listItem: @Composable (T) -> Unit = {},
    childName: String?,
    onSelect: (T) -> Unit,
    bottomIndex: Int = 0
) {

    val windowInfo = rememberWindowInfo()
    val isTablet by remember { mutableStateOf(windowInfo.screenWidthInfo != WindowInfo.WindowType.Compact) }

    val state = viewModel.state.value

    val isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    if (state.isLoading) {
        Column {
            topView()
            loading()
        }
    } else if (state.result != null) {
        Column {
            topView()
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
                        .padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = if (isTablet) 0.dp else 80.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = PaddingValues(top = 20.dp, bottom = 20.dp),
                ) {
                    items(state.result!!, itemContent = { item ->
                        Box(modifier = Modifier
                            .clickable(indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                childName?.let { child ->
                                    if (isTablet) {
                                        onSelect(item)
                                    } else {
                                        viewModel.onEvent(
                                            event = ListUIEvent.SelectAndNavigate(
                                                item = item,
                                                childScreen = child,
                                                bottomIndex = bottomIndex,
                                                isNavigate = true
                                            )
                                        )
                                    }
                                }
                            }) {
                            listItem(item)
                        }
                    })
                }
            }
        }
    } else if (state.error != null) {
        ErrorBadge(error = state.error!!)
    }
}