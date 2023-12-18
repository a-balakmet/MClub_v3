package kz.magnum.app.ui.singleViews.texts

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import aab.lib.commons.domain.models.Resource
import kz.magnum.app.ui.theme.Typography

@Composable
fun ErrorText(error: Resource.Error) {
    if (error.code == "translatable") {
        TranslatedText(
            key = "no_internet_connection",
            style = Typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp),
            color = Color(0xFFFF8200)
        )
    } else {
        Text(
            text = error.message,
            modifier = Modifier.padding(top = 16.dp),
            style = Typography.titleLarge,
            color = Color(0xFFFF8200)
        )
    }
}