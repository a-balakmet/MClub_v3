package kz.magnum.app.ui.components.aboutDocs

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.animations.ShimmerBox
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.launch
import kz.magnum.app.BuildConfig
import kz.magnum.app.R
import kz.magnum.app.domain.models.ListUIEvent
import kz.magnum.app.ui.builders.baseViews.SinglePaneListView
import kz.magnum.app.ui.singleViews.AppCardBox
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutDocsList(viewModel: AboutDocsViewModel = koinViewModel()) {

    val windowInfo = rememberWindowInfo()

    val context = LocalContext.current
    val bigLogo = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .decoderFactory(SvgDecoder.Factory())
            .data("android.resource://${context.applicationContext.packageName}/${R.raw.big_logo}")
            .size(Size.ORIGINAL)
            .build()
    )

    val version = BuildConfig.VERSION_NAME
    val interactionSource = remember { MutableInteractionSource() }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.PartiallyExpanded }
    )
    val coroutineScope = rememberCoroutineScope()
    var link by remember { mutableStateOf("") }

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch {
            sheetState.hide()
        }
    }

    SinglePaneListView(
        viewModel = viewModel,
        loading = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                for (i in 0..1) {
                    Card(
                        shape = Shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    ) {
                        ShimmerBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                        )
                    }
                }
            }
        },
        topView = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = bigLogo,
                    modifier = Modifier
                        .size(windowInfo.screenWidth / if (windowInfo.screenWidthInfo == WindowInfo.WindowType.Compact) 2 else 4),
                    contentDescription = "logo",
                    colorFilter = ColorFilter.tint(color = Primary)
                )
                Text(
                    text = stringResource(id = R.string.version, version),
                    style = Typography.titleMedium
                )

            }
        },
        listItem = {
            AppCardBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) {
                        if (it.link.contains("pdf")) {
                            viewModel.onEvent(event = ListUIEvent.OpenLink(context, it.link))
                        } else {
                            coroutineScope.launch {
                                link = it.link
                                sheetState.expand()
                            }
                        }
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = it.name, style = Typography.headlineMedium, modifier = Modifier.weight(1F))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "open doc")
                }
            }
        },
        noDataMessage = null
    )

    // Bottom dialog
    if (sheetState.isVisible) {
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                            webViewClient = WebViewClient()
                            settings.loadWithOverviewMode = true
                            settings.useWideViewPort = true
                            settings.setSupportZoom(true)
                        }
                    },
                    update = { webView ->
                        webView.loadUrl(link)
                    }
                )
                Box(modifier = Modifier.height(64.dp))
            }
        }
    }
}