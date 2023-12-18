package kz.magnum.app.ui.screens.loginBlock.otpInput

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.magnum.app.application.extenions.findActivity
import kz.magnum.app.ui.activity.MainActivity
import kz.magnum.app.ui.builders.parentViews.LoginPaneView
import kz.magnum.app.ui.singleViews.buttons.EnablingButton
import kz.magnum.app.ui.singleViews.texts.ErrorText
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.BoldFont
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun OtpInputScreen(
    viewModel: OtpInputViewModel = koinViewModel(),
    phone: String,
    onBackPressed: () -> Unit
) {

    val context = LocalContext.current
    val activity = context.findActivity() as MainActivity

    var otpValue by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    SmsRetrieverUserConsentBroadcast { _, code ->
        otpValue = code
        viewModel.setOtp(code)
    }

    LoginPaneView(
        titleText = "write_otp_sms_code",
        onBackPressed = { onBackPressed() }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TranslatedText(
                key = "send_massage_to_phone_number",
                style = Typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(text = "+7 $phone", style = Typography.titleMedium)
            BasicTextField(
                value = otpValue,
                onValueChange = {
                    if (it.length <= 4) {
                        otpValue = it
                    }
                    if (it.length == 4) {
                        keyboardController?.hide()
                        viewModel.setOtp(sms = otpValue)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 32.dp)
                    .focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    repeat(4) { index ->
                        val char = when {
                            index >= otpValue.length -> ""
                            else -> otpValue[index].toString()
                        }
                        val isFocused = otpValue.length == index
                        Box(modifier = Modifier.padding(end = if (index < 4) 16.dp else 0.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(color = MaterialTheme.colorScheme.surface)
                                    .border(
                                        width = 1.dp,
                                        color = if (isFocused || otpValue.length >= index) Primary else MaterialTheme.colorScheme.outline,
                                        shape = Shapes.medium
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = char,
                                    style = TextStyle(
                                        fontFamily = BoldFont,
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
            EnablingButton(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                isEnabled = otpValue.length == 4,
                isLoading = viewModel.exchangeState.value.isLoading,
                buttonText = "confirm"
            ) {
                viewModel.getTokens(phone, activity)
            }
            if (viewModel.counter.longValue != 0L) {
                Row(modifier = Modifier.padding(bottom = 16.dp)) {
                    TranslatedText(
                        key = "timer to resend code",
                        style = Typography.titleMedium,
                        modifier = Modifier
                    )
                    Text(text = " ${viewModel.counter.longValue} сек.", style = Typography.titleMedium)
                }
            } else {
                TranslatedText(
                    key = "timer to resend code",
                    style = Typography.titleMedium,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            otpValue = ""
                            viewModel.countDownTime(phone)
                        },
                    color = Primary
                )
            }
            // text to make a call for any help
            TranslatedText(
                key = "login problems",
                style = Typography.titleMedium,
                modifier = Modifier
            )
            Text(
                text = "7766",
                style = Typography.titleMedium,
                color = Primary,
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    val uri = Uri.parse("tel:7766")
                    val intent = Intent(Intent.ACTION_DIAL, uri)
                    try {
                        context.startActivity(intent)
                    } catch (s: SecurityException) {
                        Log.d("mClub", "OtpInputScreen: $s")
                    }
                })
            // text with an error if applicable
            viewModel.exchangeState.value.error?.let {
                ErrorText(error = it)
            }
        }
    }
}
