package kz.magnum.app.ui.components.monthProgress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.singleViews.texts.NumberedTextsList
import kz.magnum.app.ui.theme.Typography

@Composable
fun MonthProgressPage() {
    LazyColumn {
        item { Box(modifier = Modifier.padding(top = 20.dp)) }
        item { MonthProgressView(showNext = false) {} }
        item {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    TranslatedText(key = "base_bonus", style = Typography.headlineLarge)
                    TranslatedText(key = "base_bonus_desc", style = Typography.titleMedium)
                }
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    TranslatedText(key = "additional_bonus", style = Typography.headlineLarge)
                    TranslatedText(key = "additional_bonus_desc", style = Typography.titleMedium)
                }
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    TranslatedText(key = "bonus_sizes", style = Typography.headlineLarge)
                    NumberedTextsList(texts = listOf("bonus_sizes_desc_1", "bonus_sizes_desc_2", "bonus_sizes_desc_3"))
                }
                TranslatedText(key = "premium_bonus_add_1", style = Typography.titleMedium)
                TranslatedText(key = "premium_bonus_add_2", style = Typography.titleMedium, modifier = Modifier.padding(bottom = 80.dp))
            }
        }
    }
}