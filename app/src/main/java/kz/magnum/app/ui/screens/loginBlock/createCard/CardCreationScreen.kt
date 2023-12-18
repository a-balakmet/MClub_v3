package kz.magnum.app.ui.screens.loginBlock.createCard

import aab.lib.commons.ui.composableAdds.BackStackResult
import aab.lib.commons.ui.inputs.InputTextField
import aab.lib.commons.utils.MaskVisualTransformation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kz.magnum.app.R
import kz.magnum.app.ui.builders.parentViews.LoginPaneView
import kz.magnum.app.ui.navigation.NavigationActions
import kz.magnum.app.ui.singleViews.buttons.EnablingButton
import kz.magnum.app.ui.singleViews.buttons.SimpleTextButton
import kz.magnum.app.ui.singleViews.texts.ErrorText
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun CardCreationScreen(
    viewModel: CardCreationViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {

    var barcode by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
//    val keyboardController = LocalSoftwareKeyboardController.current

    viewModel.navigator.getController()?.BackStackResult<String>("barcode") {
        barcode = it
    }

    LoginPaneView(
        titleText = "enter_card_number",
        onBackPressed = { onBackPressed() }
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                InputTextField(
                    value = barcode,
                    onChange = {
                        barcode = it
                        if (it.length >= 16) focusManager.clearFocus()
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    fieldShape = Shapes.medium,
                    hintText = viewModel.cardHint,
                    hintColor = MaterialTheme.colorScheme.outline,
                    textStyle = Typography.headlineMedium,
                    modifier = Modifier.padding(top = 20.dp, bottom = 24.dp),
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    visualTransformation = MaskVisualTransformation("#### #### #### ####"),
                    showIcons = false,
                )
                IconButton(onClick = {
                    viewModel.navigator.navigate(NavigationActions.Registration.toCardScanner(), 0)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.barcode_scan),
                        contentDescription = "Back",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(48.dp)
                            .padding(end = 16.dp),
                        tint = Primary
                    )
                }
            }
            EnablingButton(
                modifier = Modifier
                    .padding(bottom = 64.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                isEnabled = barcode.length == 16,
                isLoading = viewModel.exchangeState.value.isLoading,
                buttonText = "confirm"
            ) {
                viewModel.bindCard(barcode)
            }
            TranslatedText(key = "or_create_new", style = Typography.displayLarge, modifier = Modifier.padding(bottom = 16.dp))
            TranslatedText(key = "issue_card", style = Typography.titleMedium, modifier = Modifier.padding(bottom = 24.dp))
            EnablingButton(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                isEnabled = true,
                isLoading = viewModel.exchangeState.value.isLoading,
                buttonText = "create_new"
            ) {
                viewModel.bindCard(null)
            }

            SimpleTextButton(buttonTextKey = "i_am_opt") { viewModel.navigateNext(false) }

            viewModel.exchangeState.value.error?.let {
                ErrorText(error = it)
            }
        }
    }
}