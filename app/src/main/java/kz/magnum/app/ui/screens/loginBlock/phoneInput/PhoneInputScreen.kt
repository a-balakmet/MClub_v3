package kz.magnum.app.ui.screens.loginBlock.phoneInput

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kz.magnum.app.R
import kz.magnum.app.ui.components.localeSwitcher.LocaleMenu
import kz.magnum.app.ui.singleViews.buttons.EnablingButton
import kz.magnum.app.ui.singleViews.texts.ErrorText
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneInputScreen(
    viewModel: PhoneInputViewModel = koinViewModel(),
    restore: Boolean
) {

    val context = LocalContext.current
    val windowInfo = rememberWindowInfo()
    val isTablet = remember { mutableStateOf(windowInfo.screenWidthInfo != WindowInfo.WindowType.Compact) }

    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.isStatusBarVisible = true
    systemUiController.isSystemBarsVisible = true
    systemUiController.setStatusBarColor(color = MaterialTheme.colorScheme.background)

    val contentWidth = if (isTablet.value) windowInfo.screenWidth / 2 else windowInfo.screenWidth - 32.dp

    val focusRequester = remember { FocusRequester() }
//    val focusManager = LocalFocusManager.current
     val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(contentWidth)
                            .align(Alignment.Center)
                    ) {
                        TranslatedText(key = if (restore) "resoration" else "profile_enter", style = Typography.displayLarge, modifier = Modifier)
                        TranslatedText(key = "fill_color", style = Typography.titleMedium, modifier = Modifier.padding(top = 16.dp))
                        Card(
                            modifier = Modifier
                                .padding(vertical = 32.dp)
                                .fillMaxWidth(),
                            shape = Shapes.medium,
                            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onTertiaryContainer),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onSurface,
                            )
                        ) {
                            Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                                Text(
                                    text = "+7",
                                    style = Typography.headlineMedium,
                                    modifier = Modifier
                                        .padding(start = 10.dp)
                                        .align(alignment = Alignment.CenterVertically),
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                TextField(
                                    value = viewModel.phoneNumber,
                                    textStyle = Typography.headlineMedium,
                                    modifier = Modifier
                                        .onKeyEvent {
                                            if (it.key == Key.Backspace) {
                                                viewModel.deleteLast()
                                                true
                                            } else {
                                                false
                                            }
                                        }
                                        .focusRequester(focusRequester),
                                    placeholder = {
                                        TranslatedText(
                                            key = "write_phone",
                                            style = Typography.headlineMedium,
                                            modifier = Modifier,
                                            color = MaterialTheme.colorScheme.outline,
                                            maxLines = 1
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
//                                            focusManager.clearFocus()
                                            keyboardController?.hide()
                                        }
                                    ),
                                    onValueChange = {
                                        if (it.text.length == 15) {
//                                            focusManager.clearFocus()
                                            keyboardController?.hide()
                                        }
                                        viewModel.formatNumber(it.text.replace(regex = ("[\\s (N;)-/+]").toRegex(), replacement = ""))
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        cursorColor = if (viewModel.phoneNumber.text.length >= 15) Color.Unspecified else Primary,
                                        focusedBorderColor = Color.Unspecified,
                                        unfocusedBorderColor = Color.Unspecified,
                                    )
                                )
                            }
                        }
                        EnablingButton(
                            modifier = Modifier
                                .padding(bottom = 24.dp)
                                .size(width = contentWidth, height = 50.dp),
                            isEnabled = viewModel.phoneNumber.text.length == 15,
                            isLoading = viewModel.checkPhoneState.value.isLoading,
                            buttonText = "continue"
                        ) {
                            viewModel.checkPhone()
                        }
                        TranslatedText(
                            key = "program_rules",
                            style = Typography.bodySmall,
                            modifier = Modifier
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(context.getString(R.string.club_rules))
                                    )
                                    context.startActivity(intent)
                                },
                            isHTML = true,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                        viewModel.checkPhoneState.value.error?.let { error ->
//                            focusManager.clearFocus()
                            keyboardController?.hide()
                            ErrorText(error = error)
                        }
                    }
                }
            })
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .padding(all = 24.dp)
        ) {
            LocaleMenu(menuColor = Primary)
        }
    }
}

