package kz.magnum.app.ui.components.clubs.item

import aab.lib.commons.ui.animations.ShimmerBox
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.TextView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import kotlinx.coroutines.launch
import kz.magnum.app.R
import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.ui.builders.baseViews.ItemView
import kz.magnum.app.ui.singleViews.CheckBoxWithText
import kz.magnum.app.ui.singleViews.UrlImage
import kz.magnum.app.ui.singleViews.badges.ErrorBadge
import kz.magnum.app.ui.singleViews.buttons.EnablingButton
import kz.magnum.app.ui.singleViews.buttons.SimpleTextButton
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.AppGreenColor
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubView(viewModel: ClubViewModel = koinViewModel()) {

    val context = LocalContext.current
    val textColor = MaterialTheme.colorScheme.onBackground
    var conditionsAccepted by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.PartiallyExpanded }
    )
    val coroutineScope = rememberCoroutineScope()
    var dialogType by remember { mutableStateOf("") }
    val exitType = "ask_exit_club"
    val adultType = "years_twenty_one"

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch {
            dialogType = ""
            sheetState.hide()
        }
    }

    // Dialogs
    @Composable
    fun ConfirmationDialog(club: Club) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 64.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        dialogType = ""
                        sheetState.hide()
                    }
                    viewModel.setMembership(club)
                },
                modifier = Modifier
                    .weight(1F)
                    .height(50.dp),
                shape = Shapes.medium,
                border = BorderStroke(width = 1.dp, color = Primary)
            ) {
                TranslatedText(key = "yes", style = Typography.headlineLarge, color = Primary)
            }
            OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        dialogType = ""
                        sheetState.hide()
                    }
                },
                modifier = Modifier
                    .weight(1F)
                    .height(50.dp),
                shape = Shapes.medium,
                border = BorderStroke(width = 1.dp, color = Primary)
            ) {
                TranslatedText(key = "no", style = Typography.headlineLarge, color = Primary)
            }
        }
    }

    // Content
    ItemView(viewModel = viewModel,
        loading = {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(24.dp)) {
                ShimmerBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(128.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                    )
                    HorizontalDivider()
                    for (i in 1..4) {
                        ShimmerBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp)
                        )
                    }
                }
            }
        },
        result = {
            viewModel.state.value.result?.let { club ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    // Image
                    Box(modifier = Modifier.fillMaxWidth()) {
                        UrlImage(
                            link = club.imageUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(0.dp, 0.dp, 16.dp, 16.dp)),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = club.name, style = Typography.displaySmall)
                            if (club.isMember)
                                TranslatedText(key = "in_club", style = Typography.titleLarge, color = AppGreenColor)
                        }
                        if (club.description.contains("<")) {
                            AndroidView(
                                factory = { context -> TextView(context) },
                                update = {
                                    it.text = HtmlCompat.fromHtml(club.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                                    it.setTextColor(textColor.toArgb())
                                    it.textSize = 16F
                                    it.typeface = ResourcesCompat.getFont(context, R.font.cera_regular)
                                },
                            )
                        } else {
                            Text(text = club.description, style = Typography.titleMedium)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            TranslatedText(key = "club_web", style = Typography.titleMedium, modifier = Modifier.weight(1F))
                            Image(
                                painter = painterResource(id = R.drawable.club_percent),
                                contentDescription = "percent sign",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.height(72.dp)
                            )
                        }
                        OutlinedButton(
                            onClick = {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(club.externalUrl))
                                    context.startActivity(intent)
                                } catch (s: SecurityException) {
                                    Log.d("mClub", "ClubScreen: $s")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = Shapes.medium,
                            border = BorderStroke(width = 1.dp, color = Primary)
                        ) {
                            TranslatedText(key = "go_club_site", style = Typography.headlineLarge, color = Primary)
                        }
                        if (!club.isMember) {
                            CheckBoxWithText(
                                isChecked = conditionsAccepted,
                                onChange = { conditionsAccepted = it },
                                text = "club_rules_link",
                                isHtmlText = true,
                                textStyle = Typography.titleMedium
                            ) {
                                try {
                                    val url = if (club.documentUrl.contains("htm")) club.documentUrl else "https://magnum.kz/"
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(intent)
                                } catch (s: SecurityException) {
                                    Log.d("mClub", "ClubScreen: $s")
                                }
                            }
                            EnablingButton(
                                modifier = Modifier
                                    .padding(bottom = 64.dp)
                                    .height(50.dp)
                                    .fillMaxWidth(),
                                isEnabled = conditionsAccepted,
                                isLoading = viewModel.exchangeState.value.isLoading,
                                buttonText = "enter_club"
                            ) {
                                conditionsAccepted = false
                                if (club.isAdult) {
                                    dialogType = adultType
                                    coroutineScope.launch { sheetState.expand() }
                                } else {
                                    viewModel.setMembership(club)
                                }
                            }
                        } else {
                            Box(modifier = Modifier.padding(bottom = 64.dp)) {
                                SimpleTextButton(buttonTextKey = "exit_club") {
                                    dialogType = exitType
                                    coroutineScope.launch { sheetState.expand() }
                                }
                            }
                        }
                    }
                }
            }
        },
        error = { viewModel.state.value.error?.let { ErrorBadge(error = it) } }
    )

    // Bottom dialog
    if (sheetState.isVisible && dialogType != "") {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                coroutineScope.launch {
                    dialogType = ""
                    sheetState.hide()
                }
            },
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TranslatedText(
                    key = dialogType,
                    style = Typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                viewModel.state.value.result?.let {
                    ConfirmationDialog(it)
                }
            }
        }
    }
}