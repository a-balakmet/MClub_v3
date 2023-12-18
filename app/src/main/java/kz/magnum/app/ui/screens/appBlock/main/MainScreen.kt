package kz.magnum.app.ui.screens.appBlock.main

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.magnum.app.ui.builders.parentViews.ParentNavigationView
import kz.magnum.app.ui.components.aboutDocs.AboutDocsList
import kz.magnum.app.ui.components.bonusCards.BonusCardsHorizontalList
import kz.magnum.app.ui.components.buttonsGrid.ButtonsGrid
import kz.magnum.app.ui.components.messages.list.MessagesList
import kz.magnum.app.ui.components.messages.unreadCount.MessagesCountView
import kz.magnum.app.ui.components.miniGames.MiniGamesList
import kz.magnum.app.ui.components.monthProgress.MonthProgressPage
import kz.magnum.app.ui.components.monthProgress.MonthProgressView
import kz.magnum.app.ui.components.promoCode.PromoCodeView
import kz.magnum.app.ui.navigation.Destinations.aboutScreen
import kz.magnum.app.ui.navigation.Destinations.messagesScreen
import kz.magnum.app.ui.navigation.Destinations.miniGamesScreen
import kz.magnum.app.ui.navigation.Destinations.monthProgressScreen
import kz.magnum.app.ui.navigation.Destinations.promoCodesScreen
import kz.magnum.app.ui.singleViews.AppTopBar
import kz.magnum.app.ui.singleViews.EmptyView
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = koinViewModel(),
) {

    val windowInfo = rememberWindowInfo()
    val isTablet by remember { mutableStateOf(windowInfo.screenWidthInfo != WindowInfo.WindowType.Compact) }

    ParentNavigationView(
        topBar = {
            AppTopBar(isParent = true, isMainScreen = true, navBack = {}, title = viewModel.greetings) {
                Row {
                    MessagesCountView {
                        viewModel.updateChild(messagesScreen, !isTablet)
                    }
                }
            }
        },
        leftPane = {
            Column {
                BonusCardsHorizontalList()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = if (isTablet) 0.dp else 80.dp)
                        .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    EmptyView()
                    MonthProgressView(showNext = true) { viewModel.updateChild(monthProgressScreen, !isTablet) }
                    ButtonsGrid(onButtonClick = { viewModel.updateChild(it, !isTablet) })

                    Button(onClick = { viewModel.deleteProfile() }, modifier = Modifier.padding(all = 16.dp)) {
                        Text(text = "delete profile")
                    }
                }
            }
        },
        rightPane = {
            when (viewModel.currentRoute.value) {
                messagesScreen -> MessagesList()
                monthProgressScreen -> MonthProgressPage()
                aboutScreen -> AboutDocsList()
                miniGamesScreen -> MiniGamesList()
                promoCodesScreen -> PromoCodeView()
                else -> EmptyView()
            }
        },
    )
}

