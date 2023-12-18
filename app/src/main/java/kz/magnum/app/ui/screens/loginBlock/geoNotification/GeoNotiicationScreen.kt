package kz.magnum.app.ui.screens.loginBlock.geoNotification

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import aab.lib.commons.ui.navigation.Navigator
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kz.magnum.app.R
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.components.localeSwitcher.LocaleMenu
import kz.magnum.app.ui.navigation.NavigationActions
import kz.magnum.app.ui.singleViews.buttons.CircleCloseButton
import kz.magnum.app.ui.theme.AppBlack
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.compose.koinInject

@Composable
fun GeoNotificationScreen(navigator: Navigator = koinInject()) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val context = LocalContext.current
    val windowInfo = rememberWindowInfo()
    val isTablet by remember { mutableStateOf(windowInfo.screenWidthInfo != WindowInfo.WindowType.Compact) }

    val mapImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .decoderFactory(SvgDecoder.Factory())
            .data("android.resource://${context.applicationContext.packageName}/${R.raw.map_image}")
            .build()
    )

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Primary)
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(if (isTablet) screenWidth / 2 else screenWidth )
                    .padding(horizontal = if (isTablet) 0.dp else 32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F)
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = mapImage,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize()
                                .padding(horizontal = if (isTablet) 0.dp else 32.dp),
                            contentDescription = "map image"
                        )
                    }
                    TranslatedText(key = "dear_user", style = Typography.displayLarge, modifier = Modifier, color = Color.White)
                    listOf("location_about1", "location_about2", "location_about3").forEach { text ->
                        TranslatedText(
                            key = text,
                            style = Typography.bodyMedium,
                            modifier = Modifier.padding(top = if (text == "location_about3") 0.dp else 16.dp),
                            color = Color.White
                        )
                    }
                    Button(
                        onClick = {
                            navigator.navigate(
                                navigationAction = NavigationActions.Registration.toPhoneInput(false),
                                bottomIndex = null
                            )
                        },
                        modifier = Modifier
                            .padding(vertical = 24.dp)
                            .size(width = if (isTablet) screenWidth / 2 else screenWidth - 32.dp, height = 50.dp),
                        shape = Shapes.medium,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECECEC), contentColor = AppBlack)
                    ) {
                        TranslatedText(key = "good", style = Typography.headlineLarge, modifier = Modifier, color = AppBlack)
                    }
                }
            }

            CircleCloseButton(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .padding(all = 16.dp)
            ) {
                navigator.navigate(navigationAction = NavigationActions.Registration.toPhoneInput(false), bottomIndex = null)
            }

            Box(
                modifier = Modifier
                    .align(alignment = Alignment.TopEnd)
                    .padding(all = 24.dp)
            ) {
                LocaleMenu(menuColor = Color.White)
            }
        }
    }
}
