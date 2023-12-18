package kz.magnum.app.ui.singleViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NonLazyGrid(
    columns: Int,
    itemCount: Int,
    content: @Composable (Int) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.Yellow), horizontalAlignment = Alignment.CenterHorizontally,  verticalArrangement = Arrangement.spacedBy(16.dp)) {
        var rows = (itemCount / columns)
        if (itemCount.mod(columns) > 0) {
            rows += 1
        }
        for (rowId in 0 until rows) {
            val firstIndex = rowId * columns
            Row(modifier = Modifier
                //.fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .background(color = Color.Green), horizontalArrangement = Arrangement.SpaceAround) {
               // Spacer(modifier = Modifier.weight(1F))
                for (columnId in 0 until columns) {
                    val index = firstIndex + columnId
                    Box(
                        modifier = Modifier
                            //.fillMaxWidth()
                            .weight(1f)
                    ) {
                        if (index < itemCount) {
                            content(index)
                        }
                    }
                }
               // Spacer(modifier = Modifier.weight(1F))
            }
        }
    }
}