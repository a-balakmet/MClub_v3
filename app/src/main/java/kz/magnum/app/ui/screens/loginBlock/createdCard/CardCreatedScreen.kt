package kz.magnum.app.ui.screens.loginBlock.createdCard

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kz.magnum.app.R
import kz.magnum.app.application.extenions.toChunked
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.builders.parentViews.LoginPaneView
import kz.magnum.app.ui.singleViews.buttons.EnablingButton
import kz.magnum.app.ui.singleViews.buttons.SimpleTextButton
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun CardCreatedScreen(
    viewModel: CardCreatedViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {

    val windowInfo = rememberWindowInfo()
    val isTablet = remember { mutableStateOf(windowInfo.screenWidthInfo != WindowInfo.WindowType.Compact) }

    val card = viewModel.card.collectAsState()

    LoginPaneView(
        showBackArrow = false,
        titleText = "your_card_is_ready",
        scrollableContent = true,
        onBackPressed = { onBackPressed() }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TranslatedText(
                key = "scan_for_getting_bonuses",
                style = Typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            card.value?.let { card ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .background(color = Color.Unspecified, shape = Shapes.medium)
                            .padding(bottom = 24.dp),
                    ) {
                        Image(
                            painter = painterResource(id = if (card.type == 1) R.drawable.normal_card else R.drawable.opt_card),
                            contentDescription = "normal card image",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.clip(shape = Shapes.medium)
                        )
                        if (card.type == 1) {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .align(Alignment.BottomStart)
                            ) {
                                TranslatedText(
                                    key = "purchase_bonuses",
                                    style = Typography.headlineSmall,
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    color = Color.White
                                )
                                Text(text = "+1%", style = Typography.displayMedium, color = Color.White)
                            }
                        }
                    }
                    viewModel.barcodeImage?.let {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(62.dp)
                                .background(color = Color.White, shape = Shapes.small)
                        ) {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = "barcode",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 8.dp, horizontal = 2.dp)
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = card.barcode.toChunked(4), style = Typography.bodyMedium)
                        TranslatedText(
                            key = if (card.isActive) "card_active" else "card_blocked",
                            style = Typography.bodyMedium,
                            modifier = Modifier,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1F))
            TranslatedText(key = "fill_profile", style = Typography.displayLarge, modifier = Modifier.padding(bottom = 16.dp))
            TranslatedText(key = "fill_profile_descr", style = Typography.titleMedium, modifier = Modifier.padding(bottom = 24.dp))
            EnablingButton(
                modifier = Modifier.size(width = if (isTablet.value) windowInfo.screenWidth / 2 else windowInfo.screenWidth - 32.dp, height = 50.dp),
                isEnabled = true, isLoading = false, buttonText = "fill"
            ) {
                viewModel.navigateNext(true)
            }
            SimpleTextButton(buttonTextKey = "later") { viewModel.navigateNext(false) }
        }
    }
}
