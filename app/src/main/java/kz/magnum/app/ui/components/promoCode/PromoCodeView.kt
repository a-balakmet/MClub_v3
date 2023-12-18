package kz.magnum.app.ui.components.promoCode

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import aab.lib.commons.ui.dialogs.OKDialog
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kz.magnum.app.ui.singleViews.AppCardBox
import kz.magnum.app.ui.singleViews.buttons.EnablingButton
import kz.magnum.app.ui.singleViews.texts.NumberedTextsList
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.AppOrangeColor
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoCodeView(viewModel: PromoCodeViewModel = koinViewModel()) {

    var promoCode by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val windowInfo = rememberWindowInfo()
    val isTablet by remember { mutableStateOf(windowInfo.screenWidthInfo != WindowInfo.WindowType.Compact) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.PartiallyExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch {
            sheetState.hide()
        }
    }

    val exchangeState = viewModel.exchangeState.value
    LaunchedEffect(exchangeState) {
        if (exchangeState.result != null || exchangeState.error != null) {
            promoCode = ""
        }
    }



    @Composable
    fun AboutDialogContent() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 64.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TranslatedText(key = "magnum_promocode", style = Typography.displayMedium, textAlign = TextAlign.Center)
            TranslatedText(key = "promocode_general", style = Typography.titleMedium)
            TranslatedText(key = "promocode_types", style = Typography.headlineLarge)
            NumberedTextsList(texts = listOf("promocode_refferal", "promocode_inner", "promocode_outer"))
            TranslatedText(key = "promocode_types2", style = Typography.headlineLarge)
            NumberedTextsList(texts = listOf("promocode_unique", "promocode_nonunique"))
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                TranslatedText(modifier = Modifier.weight(1F), key = "share_promocode_title", style = Typography.displayMedium)
                IconButton(onClick = {
                    coroutineScope.launch { sheetState.expand() }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                        contentDescription = "promo code help",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
        items(listOf("1", "2")) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .background(color = Primary, shape = CircleShape)
                        .size(32.dp)
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.align(Alignment.Center),
                        style = Typography.headlineLarge,
                        color = Color.White
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TranslatedText(key = "promocode_description${it}1", style = Typography.headlineSmall)
                    TranslatedText(key = "promocode_description${it}2", style = Typography.titleMedium)
                }
            }
        }

        item {
            TranslatedText(modifier = Modifier.fillMaxWidth(), key = "your_promo", style = Typography.headlineSmall, textAlign = TextAlign.Center)
        }
        item {
            AppCardBox(modifier = Modifier.fillMaxWidth()) {
                viewModel.state.value.result?.let {
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        style = Typography.displayMedium,
                        textAlign = TextAlign.Center
                    )
                }
                viewModel.state.value.error?.let {
                    Text(
                        text = it.message,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        style = Typography.displayMedium,
                        textAlign = TextAlign.Center,
                        color = AppOrangeColor
                    )
                }
            }
        }
        item {
            EnablingButton(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                isEnabled = viewModel.state.value.result != null,
                isLoading = false,
                buttonText = "share_promocode"
            ) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_SUBJECT, "Magnum Club")
                    putExtra(Intent.EXTRA_TEXT, "${viewModel.shareText}${viewModel.state.value.result?.name}")
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            }
        }
        item { TranslatedText(key = "have_promocode", style = Typography.displayMedium) }
        item { TranslatedText(key = "enter_promocode", style = Typography.titleMedium) }
        item {
            OutlinedTextField(
                value = promoCode,
                onValueChange = {
                    promoCode = it
                    if (promoCode.length == 7) {
                        //keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters, keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone = {
                    //keyboardController?.hide()
                    focusManager.clearFocus()
                }),
                shape = Shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
                        promoCode.dropLast(1)
                    },
                maxLines = 1,
                placeholder = {
                    TranslatedText(
                        modifier = Modifier.fillMaxSize(),
                        key = "i_have_promo",
                        style = Typography.headlineMedium,
                        color = MaterialTheme.colorScheme.outline,
                        textAlign = TextAlign.Center
                    )

                },
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Unspecified,
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                ),
                textStyle = Typography.headlineMedium.copy(textAlign = TextAlign.Center),
                singleLine = true
            )
        }
        item {
            EnablingButton(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                isEnabled = promoCode.length == 7,
                isLoading = viewModel.exchangeState.value.isLoading,
                buttonText = "use_promocode"
            ) {
                viewModel.activatePromoCode(promoCode)
            }
        }
    }

    // Dialog for error if any
    exchangeState.error?.let {
        OKDialog(dialogShape = Shapes.medium, text = it.message, textStyle = Typography.titleMedium) {
            viewModel.dropErrorState()
        }
    }


    // Bottom dialog
    if (sheetState.isVisible) {
        if (isTablet) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                },
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
            ) {
                AboutDialogContent()
            }
        } else {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                },
                modifier = Modifier
                    .height(windowInfo.screenHeight - 64.dp),
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
            ) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    AboutDialogContent()
                }
            }
        }
    }
}