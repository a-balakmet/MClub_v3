package kz.magnum.app.ui.builders.baseViews

import aab.lib.commons.domain.models.Resource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kz.magnum.app.ui.builders.baseViewModels.ItemViewModel

@Composable
fun <T> ItemView(
    viewModel: ItemViewModel<T>,
    loading: @Composable () -> Unit = {},
    result: @Composable (T) -> Unit = {},
    error: @Composable (Resource.Error) -> Unit = {},
) {
    val state = viewModel.state.value
    if (state.isLoading) {
        loading()
    } else if (state.result != null) {
        result(state.result!!)
    } else if (state.error != null) {
        error(state.error!!)
    } else {
        Box(modifier = Modifier)
    }
}