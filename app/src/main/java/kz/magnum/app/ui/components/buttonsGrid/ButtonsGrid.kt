package kz.magnum.app.ui.components.buttonsGrid

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.animations.ShimmerBox
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kz.magnum.app.domain.models.ListUIEvent
import kz.magnum.app.domain.models.NavigationItem
import kz.magnum.app.ui.components.coupons.CouponsInfo
import kz.magnum.app.ui.navigation.Destinations
import kz.magnum.app.ui.singleViews.AppCardBox
import kz.magnum.app.ui.singleViews.UrlImage
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.AppLightGray
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.RegularFont
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ButtonsGrid(
    viewModel: ButtonsViewModel = koinViewModel(),
    onButtonClick: (String) -> Unit
) {

    val windowInfo = rememberWindowInfo()
    val isTablet by remember { mutableStateOf(windowInfo.screenWidthInfo != WindowInfo.WindowType.Compact) }
    val availableWidth = if (isTablet) windowInfo.screenWidth / 2 else windowInfo.screenWidth

    val state = viewModel.state.value

    val context = LocalContext.current


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

    @Composable
    fun gridButton(item: NavigationItem) {
        var buildDelay by remember { mutableStateOf(false) }

        LaunchedEffect(buildDelay) {
            if (!buildDelay) {
                delay(100)
                buildDelay = true
            }
        }
        Column(
            modifier = Modifier.width(80.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppCardBox(modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    item.externalLink?.let {
                        if (it.contains("http")) {
                            viewModel.onEvent(event = ListUIEvent.OpenLink(context,it))
                        } else if (it.contains("tel")) {
                            val uri = Uri.parse("tel:7766")
                            val intent = Intent(Intent.ACTION_DIAL, uri)
                            try {
                                context.startActivity(intent)
                            } catch (s: SecurityException) {
                                Log.d("mClub", "MainScreen: $s")
                            }
                        }
                    }
                    item.action?.let {
                        when (item.name) {
                            "about_program_short" -> onButtonClick(item.name)
                            "Mini Games" -> onButtonClick(Destinations.miniGamesScreen)
                            "promocodes" -> onButtonClick(Destinations.promoCodesScreen)
                            else -> {
                                if (item.additionalValue != -1) {
//                                    if (item.name == "coupons") {
//                                        viewModel.onEvent(event = ListUIEvent.DirectNavigation(item.action))
//                                    } else {
                                    viewModel.onEvent(event = ListUIEvent.NavigateByActions(0, item.name))
//                                    }
                                } else {
                                    if (item.name == "coupons") {
                                        coroutineScope.launch { sheetState.expand() }
                                    }
                                }
                            }
                        }
                    }
                }
            ) {
                if (item.imageLink == null) {
                    if (item.isIcon) {
                        Box(modifier = Modifier) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.name,
                                modifier = Modifier
                                    .padding(all = 20.dp)
                                    .size(32.dp),
                                tint = when (item.additionalValue) {
                                    null -> Primary
                                    else -> {
                                        if (item.additionalValue < 1) {
                                            AppLightGray
                                        } else {
                                            Primary
                                        }
                                    }
                                }
                            )
                            item.additionalValue?.let {
                                if (it > 0) {
                                    Text(
                                        text = "$it",
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(all = 16.dp)
                                            .drawBehind { drawCircle(color = Primary, radius = 24F) },
                                        color = Color.White,
                                        style = TextStyle(fontFamily = RegularFont, fontSize = 12.sp),
                                    )
                                }
                            }
                        }
                    } else {
                        Image(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.name,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .padding(all = 20.dp)
                                .size(32.dp)
                        )
                    }
                } else {
                    UrlImage(
                        link = item.imageLink,
                        modifier = Modifier
                            .padding(all = 20.dp)
                            .clip(shape = Shapes.small)
                            .size(32.dp),
                        imageHeight = 32.dp,
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
            if (buildDelay) {
                if (item.translatableText) {
                    TranslatedText(key = item.name, style = Typography.bodySmall, textAlign = TextAlign.Center)
                } else {
                    Text(text = item.name, style = Typography.bodySmall, textAlign = TextAlign.Center)
                }
            }
        }
    }

    if (state.isLoading) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            maxItemsInEachRow = if (availableWidth >= 375.dp) 4 else 3
        )
        {
            for (i in 0..8) {
                Column(modifier = Modifier.width(80.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    ShimmerBox(modifier = Modifier
                        .width(70.dp)
                        .height(70.dp))
                    ShimmerBox(modifier = Modifier
                        .width(70.dp)
                        .height(12.dp))
                }

            }
        }
    } else if (state.result != null) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            maxItemsInEachRow = if (availableWidth >= 375.dp) 4 else 3
        )
        {
            val buttons = if (isTablet) {
                state.result!!.filter { it.name !in setOf("transactions_title", "my_lists") }
            } else state.result!!
            buttons.forEach { button -> gridButton(button) }
        }
    }

    // Bottom dialog
    if (sheetState.isVisible) {
        if (windowInfo.screenWidthInfo == WindowInfo.WindowType.Compact) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                },
                modifier = Modifier.height(windowInfo.screenHeight - 64.dp),
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
            ) {
                CouponsInfo(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 16.dp)
                        .verticalScroll(rememberScrollState())
                )
            }
        } else {
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
                CouponsInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 64.dp)
                )
            }
        }
    }
}