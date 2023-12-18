package kz.magnum.app.ui.screens.loginBlock.cardScanner

import aab.libs.scannerview.ScannerView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import kz.magnum.app.R
import kz.magnum.app.ui.singleViews.buttons.CircleCloseButton
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun CardScannerScreen(
    viewModel: CardScannerViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {

    val context = LocalContext.current

    val bigLogo = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .decoderFactory(SvgDecoder.Factory())
            .data("android.resource://${context.applicationContext.packageName}/${R.raw.big_logo}")
            .size(Size.ORIGINAL)
            .build()
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Scanner
        ScannerView(noPermissionText = viewModel.permissionText, noText = viewModel.noText, onScanComplete = {
            viewModel.navigateBack(it)
        })
        // Additional views
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo on top
            Image(
                painter = bigLogo,
                contentDescription = "logo"
            )
            // What to do text
            Box(
                modifier = Modifier
                    .padding(bottom = 64.dp)
                    .background(color = Color(0x7551504E), shape = Shapes.medium)
            ) {
                Text(
                    text = viewModel.scanText,
                    style = Typography.titleMedium, color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp)
                )
            }
        }

        // Field for a barcode
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(112.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        painter = painterResource(id = R.drawable.scan_shape),
                        contentDescription = "top_left",
                        tint = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.scan_shape),
                        contentDescription = "top_right",
                        modifier = Modifier.rotate(90F),
                        tint = Color.White
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        painter = painterResource(id = R.drawable.scan_shape),
                        contentDescription = "bottom_left",
                        modifier = Modifier.rotate(-90F),
                        tint = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.scan_shape),
                        contentDescription = "bottom_right",
                        modifier = Modifier.rotate(180F),
                        tint = Color.White
                    )
                }
            }
        }

        // Nav-back button
        CircleCloseButton(
            modifier = Modifier
                .padding(all = 24.dp)
                .align(Alignment.TopStart), backIcon = Icons.Default.ArrowBackIosNew
        ) {
            onBackPressed()
        }
    }

}