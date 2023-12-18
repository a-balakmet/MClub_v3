package kz.magnum.app.ui.screens.pinCode

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import kz.magnum.app.R
import kz.magnum.app.ui.singleViews.NumPad
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.singleViews.buttons.SimpleTextButton
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun PinCodeScreen(
    viewModel: PinCodeViewModel = koinViewModel(),
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val context = LocalContext.current
    val windowInfo = rememberWindowInfo()
    val isTablet = remember { mutableStateOf(windowInfo.screenWidthInfo != WindowInfo.WindowType.Compact) }

    val bigLogo = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .decoderFactory(SvgDecoder.Factory())
            .data("android.resource://${context.applicationContext.packageName}/${R.raw.big_logo}")
            .size(Size.ORIGINAL)
            .build()
    )

    var pinCode by remember { mutableStateOf("") }
    var wrongFinger by remember { mutableStateOf(false) }


    val executor = remember { ContextCompat.getMainExecutor(context) }

    LaunchedEffect(pinCode) {
        if (pinCode == viewModel.pincode) {
            viewModel.navigateNext(true)
        }
    }

    LaunchedEffect(viewModel.isFinger) {
        if (viewModel.isFinger) {
            val biometricPrompt = BiometricPrompt(
                context as FragmentActivity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        wrongFinger = true
                    }

                    @RequiresApi(Build.VERSION_CODES.R)
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        viewModel.navigateNext(true)
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        wrongFinger = true
                    }
                }
            )

            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(viewModel.fingerText)
                .setSubtitle("")
                .setDescription("")
                .setNegativeButtonText(viewModel.usePinText)
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                .build()

            biometricPrompt.authenticate(promptInfo)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(if (isTablet.value) screenWidth / 2 else screenWidth)
                .padding(horizontal = if (isTablet.value) 0.dp else 16.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (screenHeight > 650.dp) {
                Image(
                    painter = bigLogo,
                    contentDescription = "logo",
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .size(screenWidth / if (windowInfo.screenWidthInfo == WindowInfo.WindowType.Compact) 2 else 4)
                )
            }
            TranslatedText(
                key = "input_pin_code",
                style = Typography.displayMedium,
                modifier = Modifier.padding(bottom = 16.dp, top = if (screenHeight > 650.dp) 0.dp else 16.dp),
                color = Color.White
            )
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                if (pinCode.length == 4 && pinCode != viewModel.pincode) {
                    TranslatedText(
                        key = "wrong_pin",
                        style = Typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
                if (wrongFinger) {
                    TranslatedText(
                        key = "not_recognized",
                        style = Typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                for (i in 1..4) {
                    Icon(
                        imageVector = if (pinCode.length >= i) Icons.Filled.Circle else Icons.Outlined.Circle,
                        contentDescription = "dot",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                }
            }
            NumPad(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                onStart = true,
                onClick = { char ->
                    wrongFinger = false
                    when (char) {
                        "del" -> {
                            if (pinCode.isNotEmpty()) {
                                pinCode = pinCode.dropLast(1)
                            }
                        }

                        else -> {
                            if (pinCode.length != 4) {
                                pinCode += char
                            }
                        }
                    }
                }
            )
            SimpleTextButton(buttonTextKey = "forget_pin_code", color = Color.White) {
                viewModel.navigateNext(false)
            }
        }
    }
}