package kz.magnum.app.ui.singleViews.profileViews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography

@Composable
fun GenderSelector(
    selectedGender: String,
    onSelection: (String) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = Modifier.padding(bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TranslatedText(key = "set_sex", style = Typography.bodyMedium, modifier = Modifier.padding(start = 16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            listOf("male", "female").forEach {
                Box(modifier = Modifier
                    .weight(1F)
                    .clickable(indication = null, interactionSource = interactionSource) { onSelection(it) }
                    .background(color = MaterialTheme.colorScheme.surface)
                    .border(width = 1.dp, color = if (it == selectedGender) Primary else MaterialTheme.colorScheme.outline, shape = Shapes.medium)
                ) {
                    TranslatedText(
                        key = it,
                        style = Typography.titleMedium,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 16.dp),
                        color = if (it == selectedGender) Primary else MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}