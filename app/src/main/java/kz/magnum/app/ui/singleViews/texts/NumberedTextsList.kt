package kz.magnum.app.ui.singleViews.texts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kz.magnum.app.ui.theme.AppGreenColor
import kz.magnum.app.ui.theme.Typography

@Composable
fun NumberedTextsList(
    texts: List<String>,
    style: TextStyle = Typography.titleMedium,
    itemColor: Color = AppGreenColor,
    translatable: Boolean = true,
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (i in texts.indices) {
            Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(modifier = Modifier
                    .size(28.dp)
                    .background(color = itemColor, shape = CircleShape)) {
                    Text("${i+1}.", modifier = Modifier.align(Alignment.Center), style = Typography.headlineSmall, color = Color.White)
                }
                if (translatable) {
                    TranslatedText(key = texts[i], style = style, modifier = Modifier.weight(1F))
                } else {
                    Text(text = texts[i], style = style, modifier = Modifier.weight(1F))
                }
            }
        }
    }
}