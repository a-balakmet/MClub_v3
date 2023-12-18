package kz.magnum.app.ui.screens.appBlock.singleScreens

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kz.magnum.app.domain.models.Coupon
import kz.magnum.app.ui.builders.parentViews.ParentNavigationView
import kz.magnum.app.ui.components.aboutDocs.AboutDocsList
import kz.magnum.app.ui.components.campaigns.item.CampaignView
import kz.magnum.app.ui.components.campaigns.list.CampaignsList
import kz.magnum.app.ui.components.clubs.item.ClubView
import kz.magnum.app.ui.components.clubs.list.ClubsList
import kz.magnum.app.ui.components.coupons.CouponsInfo
import kz.magnum.app.ui.components.coupons.item.CouponView
import kz.magnum.app.ui.components.coupons.list.CouponsList
import kz.magnum.app.ui.components.transactions.item.TransactionView
import kz.magnum.app.ui.components.transactions.lists.ListsSelector
import kz.magnum.app.ui.singleViews.AppTopBar
import kz.magnum.app.ui.singleViews.EmptyView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentScreen(title: String, onBackPressed: () -> Unit) {

    var itemId by remember { mutableStateOf<Int?>(null) }
    var coupon by remember { mutableStateOf<Coupon?>(null) }

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

    ParentNavigationView(
        topBar = {
            AppTopBar(isParent = false, navBack = { onBackPressed() }, title = title) {
                if (title == "coupons") {
                    IconButton(onClick = {
                        coroutineScope.launch { sheetState.expand() }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                            contentDescription = "help",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        },
        leftPane = {
            when (title) {
                "clubs_title" -> ClubsList { itemId = it }
                "coupons" -> CouponsList {
                    coupon = null
                    coupon = it
                }
                "promotions" -> CampaignsList { itemId = it }
                "transactions_title" -> ListsSelector { itemId = it }
                "about_program_short" -> AboutDocsList()
                else -> Text(text = "left pane of $title")
            }
        },
        rightPane = {
            if (itemId == null) {
                EmptyView()
            } else {
                when (title) {
                    "clubs_title" -> ClubView()
                    "transactions_title" -> TransactionView()
                    "promotions" -> CampaignView()
                    else -> EmptyView()
                }
            }
            coupon?.let {
                CouponView(coupon = it, onStateChanged = { coupon = null })
            }
        }
    )

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