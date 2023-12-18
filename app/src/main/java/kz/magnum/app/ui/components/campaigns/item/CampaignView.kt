package kz.magnum.app.ui.components.campaigns.item

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.extensions.StringConvert
import aab.lib.commons.extensions.StringConvert.toDateTime
import aab.lib.commons.extensions.twoDigitsString
import aab.lib.commons.ui.animations.ShimmerBox
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import kz.magnum.app.R
import kz.magnum.app.application.extenions.toStringDate
import kz.magnum.app.data.remote.campaigns.AchievementConditions
import kz.magnum.app.ui.builders.baseViews.ItemView
import kz.magnum.app.ui.components.productsDialog.PartnerProductsList
import kz.magnum.app.ui.singleViews.AppCardBox
import kz.magnum.app.ui.singleViews.RotatedBadge
import kz.magnum.app.ui.singleViews.UrlImage
import kz.magnum.app.ui.singleViews.badges.ErrorBadge
import kz.magnum.app.ui.singleViews.buttons.EnablingButton
import kz.magnum.app.ui.singleViews.texts.ErrorText
import kz.magnum.app.ui.singleViews.texts.NumberedTextsList
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.AppGreenColor
import kz.magnum.app.ui.theme.AppYellowColor
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.RegularFont
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel
import java.text.DecimalFormat
import java.time.LocalDateTime
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignView(viewModel: CampaignViewModel = koinViewModel()) {

    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.PartiallyExpanded }
    )
    val coroutineScope = rememberCoroutineScope()
    var dialogType by remember { mutableStateOf("") }

    val windowInfo = rememberWindowInfo()
    val isTablet by remember { mutableStateOf(windowInfo.screenWidthInfo != WindowInfo.WindowType.Compact) }

    var selectedCondition by remember { mutableStateOf<AchievementConditions?>(null) }
    var selectedStickerName by remember { mutableStateOf<String?>(null) }
    var selectedStickerCount by remember { mutableStateOf(0) }
    var selectedCouponDate by remember { mutableStateOf<LocalDateTime?>(null) }
    var selectedAchievementID by remember { mutableStateOf<Int?>(null) }

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch {
            dialogType = ""
            sheetState.hide()
        }
    }

    val exchangeState = viewModel.exchangeState.value

    fun getStickersName(quantity: Int): String {
        return when (quantity) {
            1 -> "1sticker"
            2, 3, 4 -> "234sticker"
            else -> "stickers"
        }
    }

    @Composable
    fun StickersDialog() {
        selectedCondition?.let { condition ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = condition.coupon.name, style = Typography.displaySmall)
                Text(text = condition.coupon.subtitle, style = Typography.headlineSmall)
                RotatedBadge(
                    text = if (condition.usedCount < condition.limit && selectedStickerCount >= condition.cost) "available_coupon" else "non_available_coupon",
                    textColor = if (condition.usedCount < condition.limit && selectedStickerCount >= condition.cost) Color.White else MaterialTheme.colorScheme.onTertiaryContainer,
                    textStyle = Typography.titleMedium,
                    backgroundColor = if (condition.usedCount < condition.limit && selectedStickerCount >= condition.cost) AppGreenColor else MaterialTheme.colorScheme.tertiaryContainer,
                    translatable = true
                )
                Text(text = condition.coupon.description.replace("\n", ""), style = Typography.titleMedium)
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    TranslatedText(key = "what_gives", style = Typography.headlineLarge)
                    Text(text = condition.coupon.type.replace("\n", ""), style = Typography.titleMedium)
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    TranslatedText(key = "how_works", style = Typography.headlineLarge)
                    Text(text = condition.coupon.instructions.replace("\n", ""), style = Typography.titleMedium)
                }
                selectedCouponDate?.let { dateUntil ->
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        TranslatedText(key = "active_until", style = Typography.headlineLarge)
                        Text(
                            text = "${dateUntil.dayOfMonth.twoDigitsString()}.${dateUntil.monthValue.twoDigitsString()}.${dateUntil.year}",
                            style = Typography.titleMedium
                        )
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    TranslatedText(key = "sticker_price", style = Typography.headlineLarge)
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(text = "${condition.cost}", style = Typography.titleMedium)
                        if (selectedStickerName == null) {
                            val key = getStickersName(selectedStickerCount)
                            TranslatedText(key = key, style = Typography.titleMedium)
                        } else {
                            Text(text = selectedStickerName!!, style = Typography.titleMedium)
                        }
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    TranslatedText(key = "sticker_available", style = Typography.headlineLarge)
                    Text(text = "${condition.limit - condition.usedCount}", style = Typography.titleMedium)
                }
                exchangeState.error?.let {
                    ErrorText(error = it)
                }
                EnablingButton(
                    modifier = Modifier
                        .padding(bottom = 64.dp)
                        .align(Alignment.CenterHorizontally)
                        .size(width = (if (isTablet) windowInfo.screenWidth / 2 else windowInfo.screenWidth) - 32.dp, height = 50.dp),
                    isEnabled = true,
                    isLoading = viewModel.exchangeState.value.isLoading,
                    buttonText = "change"
                ) {
                    viewModel.changeStickers(condition.uuid,  selectedAchievementID ?: 0)
                }

            }
        }
    }

    ItemView(
        viewModel = viewModel,
        loading = {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(24.dp)) {
                ShimmerBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
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
        result = { campaign ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Image and available badges
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column {
                            UrlImage(
                                link = campaign.squareImage,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp)),
                                contentScale = ContentScale.FillWidth
                            )
                            Box(modifier = Modifier.height(16.dp))
                        }
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .align(Alignment.BottomEnd),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            campaign.badges?.let { badges ->
                                badges.forEach { text ->
                                    RotatedBadge(text = text, textColor = Primary, textStyle = Typography.titleSmall, backgroundColor = AppYellowColor)
                                }
                            }
                        }
                    }
                    // Period
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        TranslatedText(key = "condition_details", style = Typography.displaySmall, modifier = Modifier.padding(top = 16.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            TranslatedText(key = "promo_date_range", style = Typography.titleLarge, color = Primary)
                            Text(
                                text = "${campaign.startDateTime().toStringDate(campaign.startDateTime().year != campaign.stopDateTime().year)} - ${
                                    campaign.stopDateTime().toStringDate(campaign.startDateTime().year != campaign.stopDateTime().year)
                                }",
                                style = Typography.titleLarge, color = Primary
                            )
                        }
                        HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
                    }
                    // Content
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Box(modifier = Modifier.height(16.dp))
                        // Achievements
                        campaign.clientAchievement?.let { achievement ->
                            AppCardBox(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 14.dp, horizontal = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (achievement.achievement.stickerImage != null) {
                                        UrlImage(link = achievement.achievement.stickerImage, modifier = Modifier.size(36.dp))
                                    } else {
                                        AsyncImage(
                                            model = "android.resource://${context.applicationContext.packageName}/${R.raw.sticker_badge}",
                                            contentDescription = "sticker",
                                        )
                                    }
                                    Column {
                                        TranslatedText(key = "my_balance", style = Typography.bodyMedium)
                                        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                            Text(text = "${campaign.clientAchievement.stickersCount}", style = Typography.headlineLarge)
                                            if (campaign.clientAchievement.achievement.stickerName.isNullOrEmpty()) {
                                                val key = getStickersName(campaign.clientAchievement.stickersCount)
                                                TranslatedText(key = key, style = Typography.headlineLarge)
                                            } else {
                                                Text(text = campaign.clientAchievement.achievement.stickerName, style = Typography.headlineLarge)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // Chances
                        campaign.chancesSum?.let {
                            if (it > 0.5) {
                                AppCardBox(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 14.dp, horizontal = 12.dp),
                                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .background(color = AppYellowColor, shape = Shapes.medium)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.horse_shoe),
                                                contentDescription = "chances icons",
                                                modifier = Modifier.align(Alignment.Center),
                                                tint = Color.White
                                            )
                                        }
                                        Column {
                                            TranslatedText(key = "chance_to_win", style = Typography.bodyMedium)
                                            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                                Text(text = "${campaign.chancesSum.roundToInt()}", style = Typography.headlineLarge)
                                                val key = when (campaign.chancesSum.roundToInt()) {
                                                    1 -> "1chances"
                                                    2, 3, 4 -> "234chances"
                                                    else -> "chances"
                                                }
                                                TranslatedText(key = key, style = Typography.headlineLarge)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // Descriptions
                        if (campaign.shortDescription.length > 3) {
                            Text(
                                text = campaign.shortDescription,
                                style = Typography.headlineLarge,
                            )
                        }
                        if (campaign.fullDescription != null) {
                            Text(
                                text = campaign.fullDescription,
                                style = Typography.titleMedium,
                            )
                        }
                        campaign.shortConditions?.let {
                            if (it.length > 3) {
                                Text(
                                    text = it,
                                    style = Typography.titleMedium,
                                    // modifier = Modifier.padding(top = 24.dp)
                                )
                            }
                        }
                        campaign.fullConditions?.let {
                            if (it.length > 3) {
                                Text(
                                    text = it,
                                    style = Typography.titleMedium,
                                )
                            }
                        }
                        // Conditions
                        campaign.conditionsBlock?.let { conditions ->
                            conditions.forEach { condition ->
                                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                    condition.title?.let {
                                        Text(text = it, style = Typography.titleMedium)
                                    }
                                    condition.description?.let {
                                        Text(
                                            text = it.replace("\\n", ""),
                                            style = Typography.titleMedium
                                        )
                                    }
                                    condition.conditions?.let { c ->
                                        NumberedTextsList(texts = c, style = Typography.titleMedium, itemColor = Primary, translatable = false)
                                    }
                                }
                            }
                        }
                        // Disclaimer
                        campaign.disclaimer?.let {
                            Text(
                                text = it,
                                style = TextStyle(
                                    fontFamily = RegularFont,
                                    fontSize = 12.sp,
                                    fontStyle = FontStyle.Italic
                                ),
                            )
                        }
                        // Achievements / coupons
                        campaign.clientAchievement?.let { achievement ->
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                TranslatedText(key = "coupons", style = Typography.displaySmall, modifier = Modifier.padding(bottom = 16.dp))
                                achievement.achievement.conditions.forEach { item ->
                                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        // Coupon's image
                                        Box(modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                selectedCondition = item
                                                selectedStickerName = achievement.achievement.stickerName
                                                selectedStickerCount = achievement.stickersCount
                                                selectedCouponDate = achievement.achievement.endDate
                                                    .substring(0, 19)
                                                    .toDateTime(StringConvert.dateTimeTFormatter)
                                                selectedAchievementID = achievement.achievement.id
                                                dialogType = "coupon"
                                                coroutineScope.launch { sheetState.expand() }
                                            }) {
                                            UrlImage(
                                                link = item.coupon.imageUrl,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(shape = Shapes.medium)
                                            )
                                            Column(modifier = Modifier.padding(all = 16.dp)) {
                                                Text(text = item.coupon.name, style = Typography.headlineMedium, color = Color.White)
                                                Text(text = item.coupon.subtitle, style = Typography.titleSmall, color = Color.White)
                                            }
                                        }
                                        // Available stickers and cost
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                TranslatedText(key = "get_discount", style = Typography.headlineSmall)
                                                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                                    Text(text = "${item.cost}", style = Typography.headlineSmall, color = Primary)
                                                    if (achievement.achievement.stickerName.isNullOrEmpty()) {
                                                        val key = when (campaign.clientAchievement.stickersCount) {
                                                            1 -> "1sticker"
                                                            2, 3, 4 -> "234sticker"
                                                            else -> "stickers"
                                                        }
                                                        TranslatedText(key = key, style = Typography.headlineSmall, color = Primary)
                                                    } else {
                                                        Text(text = achievement.achievement.stickerName, style = Typography.headlineSmall, color = Primary)
                                                    }
                                                }
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .background(color = AppYellowColor, shape = Shapes.extraSmall)
                                                    .defaultMinSize(minWidth = 77.dp),
                                                contentAlignment = Alignment.CenterEnd
                                            ) {
                                                Column(
                                                    modifier = Modifier.padding(start = 10.dp, top = 5.dp, end = 5.dp, bottom = 5.dp),
                                                    horizontalAlignment = Alignment.End
                                                ) {
                                                    Text(
                                                        text = "${DecimalFormat().format(item.achievementPrices.oldPrice)} ₸",
                                                        style = TextStyle(
                                                            fontFamily = RegularFont,
                                                            fontSize = 12.sp,
                                                            textDecoration = TextDecoration.LineThrough
                                                        ),
                                                        color = Color.Black
                                                    )
                                                    Text(
                                                        text = "${DecimalFormat().format(item.achievementPrices.newPrice)} ₸",
                                                        style = Typography.headlineSmall,
                                                        color = Color.Black
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // Download link
                        campaign.downloadLink?.let {
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                                    context.startActivity(intent)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                //.padding(top = 24.dp),
                                shape = Shapes.small,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                    contentColor = Primary,
                                )
                            ) {
                                TranslatedText(key = "full_promotion_rules", style = Typography.headlineLarge, color = Primary)
                            }
                        }
                        // Info button
                        campaign.infoButton?.let { info ->
                            if (!info.link.isNullOrEmpty() && !info.title.isNullOrEmpty()) {
                                Button(
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(info.link))
                                        context.startActivity(intent)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    shape = Shapes.small,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                        contentColor = Primary,
                                    )
                                ) {
                                    Text(text = info.title, style = Typography.headlineLarge, color = Primary)
                                }
                            }
                        }
                        // Products of campaign
                        campaign.withProducts?.let {
                            if (it) {
                                Button(
                                    onClick = {
                                        viewModel.saveQueryType().invokeOnCompletion {
                                            dialogType = "partner_products"
                                            coroutineScope.launch { sheetState.expand() }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    shape = Shapes.medium,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                        contentColor = Primary,
                                    )
                                ) {
                                    TranslatedText(key = "partner_products", style = Typography.headlineLarge, textAlign = TextAlign.Center, color = Primary)
                                }
                            }
                        }
                        Box(modifier = Modifier.height(80.dp))
                    }
                }
        },
        error = { ErrorBadge(error = it) }
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
            modifier = Modifier
                .height(windowInfo.screenHeight - 64.dp),
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (dialogType == "dialogType") {
                    TranslatedText(
                        key = dialogType,
                        style = Typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                when (dialogType) {
                    "partner_products" -> PartnerProductsList()
                    "coupon" -> StickersDialog()
                }
            }
        }
    }
}