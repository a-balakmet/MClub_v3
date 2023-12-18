package kz.magnum.app.ui.components.transactions.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography

@Composable
fun ListsSelector(onSelect: (Int) -> Unit) {

    var selectedList by remember { mutableStateOf("purchases") }
    val interactionSource = remember { MutableInteractionSource() }

    @Composable
    fun Selector(modifier: Modifier, text: String) {
        Box(modifier = modifier
            .background(color = if (selectedList == text) Primary else MaterialTheme.colorScheme.surface, shape = Shapes.medium)
            .border(
                width = 1.dp,
                color = if (selectedList == text) Primary else MaterialTheme.colorScheme.surfaceTint,
                shape = Shapes.medium
            )
            .clickable(indication = null, interactionSource = interactionSource) {
                if (selectedList != text) {
                    selectedList = text
                }
            }
        ) {
            TranslatedText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                key = text,
                style = Typography.bodyMedium,
                color = if (selectedList == text) Color.White else MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(all = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            listOf("purchases", "burning_bonus").forEach {
                Selector(modifier = Modifier.weight(1F), text = it)
            }
        }
        if (selectedList == "purchases") {
            TransactionsList(onSelect = { onSelect(it) })
        } else {
            BurningsList()
        }
    }
}