package kz.magnum.app.ui.singleViews.buttons

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import aab.lib.commons.ui.animations.ShimmerBox
import androidx.compose.ui.text.style.TextAlign
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography

@Composable
fun EnablingButton(
    modifier: Modifier,
    isEnabled: Boolean,
    isLoading: Boolean,
    buttonText: String,
    onClick: () -> Unit
) {
    if (isLoading) {
        ShimmerBox(modifier = modifier, shape = Shapes.medium)
    } else {
        Button(
            onClick = { if (isEnabled) onClick() },
            modifier = modifier,
            shape = Shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isEnabled) Primary else MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = if (isEnabled) Color.White else MaterialTheme.colorScheme.onTertiaryContainer,
            )
        ) {
            TranslatedText(
                key = buttonText,
                style = Typography.headlineLarge,
                textAlign = TextAlign.Center,
                color = if (isEnabled) Color.White else MaterialTheme.colorScheme.onTertiaryContainer,
            )
        }
    }
}