package kz.magnum.app.ui.components.coupons.item

import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import aab.lib.commons.ui.dialogs.OKDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kz.magnum.app.application.extenions.toStringDate
import kz.magnum.app.domain.models.Coupon
import kz.magnum.app.ui.components.productsDialog.PartnerProductsList
import kz.magnum.app.ui.singleViews.UrlImage
import kz.magnum.app.ui.singleViews.buttons.EnablingButton
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponView(coupon: Coupon, viewModel: CouponViewModel = koinViewModel(), onStateChanged: () -> Unit) {

    val couponState by remember { mutableStateOf(coupon.stateInt) }
    val state = viewModel.exchangeState.value

    val windowInfo = rememberWindowInfo()

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

    LaunchedEffect(state) {
        if (state.result != null) {
            viewModel.dropState()
            onStateChanged()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(color = colorResource(id = coupon.couponColor), shape = Shapes.medium)
        ) {
            UrlImage(
                link = coupon.imageLink,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(shape = Shapes.medium),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier.padding(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = coupon.name, style = Typography.displaySmall, color = Color.White)
                Text(text = coupon.value, style = Typography.titleLarge, color = Color.White)
                Spacer(modifier = Modifier.weight(1F))
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    TranslatedText(key = "active_coupon_2", style = Typography.bodyLarge, color = Color.White)
                    Text(text = coupon.dateTo.toStringDate(showYear = true), style = Typography.bodyLarge, color = Color.White)
                }
            }
        }
        Text(text = coupon.name.replace("\n", ""), style = Typography.displayMedium)
        Text(text = coupon.value.replace("\n", ""), style = Typography.headlineMedium)
        if (coupon.stateInt == 1)
            EnablingButton(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(), isEnabled = true, isLoading = state.isLoading, buttonText = "activate"
            ) {
                viewModel.changeCouponState(coupon)
            }
        if (coupon.stateInt == 2)
            EnablingButton(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(), isEnabled = true, isLoading = state.isLoading, buttonText = "deactivate"
            ) {
                viewModel.changeCouponState(coupon)
            }
        Text(text = coupon.description.replace("\n", ""), style = Typography.bodyMedium)
        TranslatedText(key = "what_gives", style = Typography.headlineMedium)
        Text(text = coupon.type.replace("\n", ""), style = Typography.bodyMedium)
        TranslatedText(key = "how_works", style = Typography.headlineMedium)
        Text(text = coupon.instructions.replace("\n", ""), style = Typography.bodyMedium)
        TranslatedText(key = "active_until", style = Typography.headlineMedium)
        Text(text = coupon.dateTo.toStringDate(showYear = true), style = Typography.bodyMedium)

        if (coupon.withProducts)
            Button(
                onClick = {
                    viewModel.saveQueryType(coupon.couponId).invokeOnCompletion {
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
    Box(modifier = Modifier.height(80.dp))


    state.error?.let {
        OKDialog(dialogShape = Shapes.medium, text = it.message, textStyle = Typography.titleMedium) {}
    }

    if (sheetState.isVisible) {
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TranslatedText(
                    key = "partner_products",
                    style = Typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                PartnerProductsList()
            }
        }
    }
}

