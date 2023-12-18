package kz.magnum.app.ui.screens.commons.optInfo

import aab.lib.commons.ui.inputs.InputTextField
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kz.magnum.app.R
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.builders.parentViews.LoginPaneView
import kz.magnum.app.ui.singleViews.CheckBoxWithText
import kz.magnum.app.ui.singleViews.buttons.EnablingButton
import kz.magnum.app.ui.singleViews.texts.ErrorText
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun WholesaleInfoScreen(
    viewModel: WholesaleViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {

    val context = LocalContext.current

    var iin by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    var conditionsAccepted by remember { mutableStateOf(false) }

    LoginPaneView(
        titleText = "opt_card",
        scrollableContent = true,
        onBackPressed = { onBackPressed() }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.opt_card),
                contentDescription = "wholesale card image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .clip(shape = Shapes.medium)
                    .fillMaxWidth()
            )
            TranslatedText(key = "why_opt_title", style = Typography.bodyMedium, modifier = Modifier.padding(vertical = 16.dp), isHTML = true)
            TranslatedText(key = "why_opt_dscr", style = Typography.bodyMedium, modifier = Modifier, isHTML = true)
            TranslatedText(
                key = "magnumopt.kz",
                style = Typography.bodyMedium,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://magnumopt.kz")
                        )
                        context.startActivity(intent)
                    },
                isHTML = true
            )
            TranslatedText(key = "input_iin", style = Typography.displayLarge, modifier = Modifier)
            InputTextField(
                value = iin,
                onChange = {
                    iin = it
                    if (iin.length == 12) {
                        keyboardController?.hide()
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                fieldShape = Shapes.medium,
                hintText = viewModel.iin,
                textStyle = Typography.headlineMedium,
                modifier = Modifier.padding(vertical = 16.dp),
                focusedBorderColor = Primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                showIcons = false
            )
            CheckBoxWithText(
                isChecked = conditionsAccepted,
                onChange = { conditionsAccepted = it },
                text = "program_rules",
                textStyle = Typography.titleMedium,
                isHtmlText = true,
                textOnClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(context.getString(R.string.club_rules))
                    )
                    context.startActivity(intent)
                }
            )
            EnablingButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                isEnabled = conditionsAccepted && iin.length == 12,
                isLoading = viewModel.exchangeState.value.isLoading,
                buttonText = "forward"
            ) {
                viewModel.postIIN(iin)
            }
            Box(modifier = Modifier.height(16.dp))

            viewModel.exchangeState.value.error?.let {
                ErrorText(error = it)
            }
        }
    }
}