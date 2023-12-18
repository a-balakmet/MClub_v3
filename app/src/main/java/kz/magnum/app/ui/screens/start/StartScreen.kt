package kz.magnum.app.ui.screens.start

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kz.magnum.app.R
import kz.magnum.app.ui.theme.Primary
import org.koin.androidx.compose.koinViewModel

@Composable
fun StartScreen(viewModel: StartScreenViewModel = koinViewModel()) {

    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.isStatusBarVisible = true
    systemUiController.isSystemBarsVisible = true
    systemUiController.setStatusBarColor(color = Primary, darkIcons = true)

    val context = LocalContext.current
    val bigLogo = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .decoderFactory(SvgDecoder.Factory())
            .data("android.resource://${context.applicationContext.packageName}/${R.raw.big_logo}")
            .size(Size.ORIGINAL)
            .build()
    )

    val windowInfo = rememberWindowInfo()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Primary)
    ) {
        Image(
            painter = bigLogo,
            modifier = Modifier
                .align(Alignment.Center)
                .size(windowInfo.screenWidth / if (windowInfo.screenWidthInfo == WindowInfo.WindowType.Compact) 1 else 3)
                .padding(horizontal = 85.dp),
            contentDescription = null
        )
    }
}