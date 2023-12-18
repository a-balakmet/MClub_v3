package kz.magnum.app.ui.singleViews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.magnum.app.ui.theme.Shapes

@Composable
fun AppCardBox(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface, shape = Shapes.medium)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceTint,
                shape = Shapes.medium
            )
    ) {
        content()
    }
}