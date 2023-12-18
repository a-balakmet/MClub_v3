package kz.magnum.app.ui.screens.appBlock.singleScreens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kz.magnum.app.domain.models.Coupon
import kz.magnum.app.ui.builders.parentViews.SinglePaneView
import kz.magnum.app.ui.components.aboutDocs.AboutDocsList
import kz.magnum.app.ui.components.campaigns.item.CampaignView
import kz.magnum.app.ui.components.coupons.item.CouponView
import kz.magnum.app.ui.components.messages.list.MessagesList
import kz.magnum.app.ui.components.miniGames.MiniGamesList
import kz.magnum.app.ui.components.monthProgress.MonthProgressPage
import kz.magnum.app.ui.components.promoCode.PromoCodeView
import kz.magnum.app.ui.components.transactions.item.TransactionView
import kz.magnum.app.ui.navigation.Destinations.aboutScreen
import kz.magnum.app.ui.navigation.Destinations.campaignScreen
import kz.magnum.app.ui.navigation.Destinations.couponScreen
import kz.magnum.app.ui.navigation.Destinations.messagesScreen
import kz.magnum.app.ui.navigation.Destinations.miniGamesScreen
import kz.magnum.app.ui.navigation.Destinations.monthProgressScreen
import kz.magnum.app.ui.navigation.Destinations.promoCodesScreen
import kz.magnum.app.ui.navigation.Destinations.transactionScreen

@Composable
fun CommonDetailScreen(title: String, coupon: Coupon? = null, onBackPressed: () -> Unit) {

    SinglePaneView(
        titleKey = when(title) {
            promoCodesScreen -> "promocodes"
            transactionScreen -> "transactions_title"
            else -> title
        },
        content = {
            when (title) {
                messagesScreen -> MessagesList()
                monthProgressScreen -> MonthProgressPage()
                campaignScreen -> CampaignView()
                aboutScreen -> AboutDocsList()
                miniGamesScreen -> MiniGamesList()
                promoCodesScreen -> PromoCodeView()
                transactionScreen -> TransactionView()
                couponScreen -> coupon?.let {
                    CouponView(coupon = it) { onBackPressed() }
                }
                else -> Text(text = "screen of $title  - UNDER CONSTRUCTION")
            }
        },
        navBack = { onBackPressed() }
    )
}