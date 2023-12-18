package kz.magnum.app.ui.singleViews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Shapes

@Composable
fun RotatedBadge(
    text: String,
    textColor: Color,
    textStyle: TextStyle,
    backgroundColor: Color,
    borderWidth: Dp = 1.dp,
    translatable: Boolean = false
) {
    Box(
        modifier = Modifier
            .rotate(-4F),
    ) {
        Box(
            modifier = Modifier
                .background(color = backgroundColor, shape = Shapes.small)
                .border(
                    width = borderWidth,
                    color = MaterialTheme.colorScheme.surface,
                    shape = Shapes.small
                )
        ) {
            if (translatable) {
                TranslatedText(key = text, style = textStyle, color = textColor, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp))
            } else {
                Text(text = text, style = textStyle, color = textColor, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp))
            }
        }
    }
}