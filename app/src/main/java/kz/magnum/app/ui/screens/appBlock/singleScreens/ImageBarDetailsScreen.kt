package kz.magnum.app.ui.screens.appBlock.singleScreens

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.magnum.app.ui.singleViews.buttons.CircleCloseButton
import kz.magnum.app.ui.components.campaigns.item.CampaignView
import kz.magnum.app.ui.components.clubs.item.ClubView
import kz.magnum.app.ui.navigation.Destinations.campaignScreen
import kz.magnum.app.ui.navigation.Destinations.clubScreen
import kz.magnum.app.ui.navigation.NavigationViewModel
import kz.magnum.app.ui.singleViews.EmptyView
import org.koin.androidx.compose.koinViewModel

@Composable
fun ImageAppBarDetailsScreen(
    navigator: NavigationViewModel = koinViewModel(),
    type: String,
    onBackPressed: () -> Unit
) {

    val windowInfo = rememberWindowInfo()

    SideEffect {
        if (windowInfo.screenWidthInfo != WindowInfo.WindowType.Compact) {
            navigator.navigateByIndex()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (type) {
            campaignScreen -> CampaignView()
            clubScreen -> ClubView()
            else -> EmptyView()
        }
        CircleCloseButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(all = 24.dp)
        ) {
            onBackPressed()
        }
    }

}