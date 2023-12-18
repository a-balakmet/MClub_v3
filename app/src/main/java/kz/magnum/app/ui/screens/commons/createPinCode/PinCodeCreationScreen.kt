package kz.magnum.app.ui.screens.commons.createPinCode

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kz.magnum.app.R
import kz.magnum.app.ui.singleViews.NumPad
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.builders.parentViews.LoginPaneView
import kz.magnum.app.ui.singleViews.buttons.EnablingButton
import kz.magnum.app.ui.singleViews.buttons.SimpleTextButton
import kz.magnum.app.ui.theme.AppOrangeColor
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinCodeCreationScreen(
    viewModel: PinCodeCreationViewModel = koinViewModel(),
    showBackArrow: Int,
    onBackPressed: () -> Unit,
) {

    var firstPin by remember { mutableStateOf("") }
    var secondPin by remember { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.PartiallyExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    LaunchedEffect(secondPin) {
        if (secondPin.length == 4 && secondPin == firstPin) {
            if (viewModel.hasBiometric) {
                coroutineScope.launch { sheetState.expand() }
            } else {
                viewModel.navigateNext(firstPin, false)
            }
        }
    }

    @Composable
    fun ScreenContents() {
        Column {
            TranslatedText(key = "create_pin_long", style = Typography.titleMedium, modifier = Modifier.padding(bottom = 32.dp))
            Column(modifier = Modifier.align(Alignment.CenterHorizontally), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(firstPin, secondPin).forEach { item ->
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                        for (i in 1..4) {
                            Icon(
                                imageVector = if (item.length >= i) Icons.Filled.Circle else Icons.Outlined.Circle,
                                contentDescription = "dot",
                                modifier = Modifier.size(24.dp),
                                tint = if (item.length >= i) Primary else MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxSize()
            ) {
                if (firstPin.length == 4 && secondPin.length != 4) {
                    TranslatedText(key = "repeat_pin1", style = Typography.titleMedium, modifier = Modifier.align(Alignment.Center))
                }
                if (firstPin.length == 4 && secondPin.length == 4) {
                    if (firstPin != secondPin) {
                        TranslatedText(key = "do_not_match_pin", style = Typography.titleMedium, modifier = Modifier.align(Alignment.Center), color = AppOrangeColor)
                    }
                }
            }
            NumPad(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F), onClick = { char ->
                    when (char) {
                        "del" -> {
                            if (secondPin.isNotEmpty()) {
                                secondPin = secondPin.dropLast(1)
                            } else if (secondPin.isEmpty()) {
                                if (firstPin.isNotEmpty()) {
                                    firstPin = firstPin.dropLast(1)
                                }
                            }
                        }

                        else -> {
                            if (firstPin.length != 4) {
                                firstPin += char
                            } else {
                                if (secondPin.length != 4) {
                                    secondPin += char
                                }
                            }
                        }
                    }
                }
            )
            if (showBackArrow == 0) {
                SimpleTextButton(buttonTextKey = "later") { viewModel.navigateNext(null, false) }
            }

            // Bottom dialog
            if (sheetState.isVisible) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        coroutineScope.launch { sheetState.hide() }
                        viewModel.navigateNext(firstPin, false)
                    },
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp, horizontal = 16.dp)
                        //.height(256.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        ) {
                            TranslatedText(key = "use_finger", style = Typography.displaySmall, modifier = Modifier.padding(end = 48.dp))
                            IconButton(onClick = { coroutineScope.launch { sheetState.hide() } }) {
                                Icon(
                                    imageVector = Icons.Rounded.AddCircleOutline,
                                    contentDescription = "close",
                                    modifier = Modifier.rotate(45F),
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }

                        Row {
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.fingerprint),
                                    contentDescription = "fingerprint",
                                    modifier = Modifier
                                        .padding(end = 16.dp)
                                        .size(58.dp),
                                    tint = Primary
                                )
                                TranslatedText(key = "use_finger_why", style = Typography.titleMedium, modifier = Modifier.weight(1F))
                            }
                        }
                        EnablingButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp)
                                .height(50.dp), isEnabled = true, isLoading = false, buttonText = "use"
                        ) {
                            coroutineScope.launch { sheetState.hide() }
                            viewModel.navigateNext(firstPin, true)
                        }
                    }
                }
            }
        }
    }


    when (showBackArrow) {
        1 -> { // single pane
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "") },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = "Back",
                                    modifier = Modifier.size(32.dp),
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        },
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
                            modifier = Modifier.fillMaxSize()
                        ) {
                            TranslatedText(
                                key = "create_pin_short",
                                style = if (screenHeight > 650.dp) Typography.displayLarge else Typography.displayMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            ScreenContents()
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        }

        2 -> { // two panes
            Text(text = "two panes")
        }

        0 -> { // during registration
            LoginPaneView(
                showBackArrow = false,
                onBackPressed = { },
                titleText = "create_pin_short"
            ) {
                ScreenContents()
            }
        }
    }
}