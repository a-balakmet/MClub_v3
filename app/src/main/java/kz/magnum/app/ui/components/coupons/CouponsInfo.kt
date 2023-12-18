package kz.magnum.app.ui.components.coupons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kz.magnum.app.R
import kz.magnum.app.ui.singleViews.texts.NumberedTextsList
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Typography

@Composable
fun CouponsInfo(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_percent),
            contentDescription = "percent sign",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth()
        )
        TranslatedText(key = "coupons_about", style = Typography.titleMedium)
        TranslatedText(key = "coupons_how_works", style = Typography.headlineMedium)
        TranslatedText(key = "coupons_how_works_full", style = Typography.titleMedium)
        TranslatedText(key = "coupons_how_to", style = Typography.headlineMedium)
        NumberedTextsList(texts = listOf("coupons_about1", "coupons_about2", "coupons_about3", "coupons_about4"), style = Typography.titleMedium)
    }
}