package kz.magnum.app.ui.singleViews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes

@Composable
fun CheckBoxWithText(
    isChecked: Boolean,
    onChange: (Boolean) -> Unit,
    text: String,
    textStyle: TextStyle,
    isHtmlText: Boolean = false,
    textOnClick: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier.clickable(indication = null, interactionSource = interactionSource) { onChange(!isChecked) },
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    shape = Shapes.extraSmall,
                    color = if (isChecked) Primary else Color.Transparent
                )
                .border(
                    width = 1.dp,
                    color = if (isChecked) Color.Transparent else MaterialTheme.colorScheme.outline,
                    shape = Shapes.extraSmall
                )
        ) {
            if (isChecked)
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "check",
                    modifier = Modifier.padding(all = 2.dp),
                    tint = Color.White
                )
        }
        TranslatedText(
            key = text,
            style = textStyle,
            modifier = Modifier.clickable(indication = null, interactionSource = interactionSource) { textOnClick() },
            isHTML = isHtmlText
        )
    }
}