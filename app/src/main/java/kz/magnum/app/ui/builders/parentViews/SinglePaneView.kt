package kz.magnum.app.ui.builders.parentViews

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import kz.magnum.app.ui.navigation.NavigationViewModel
import kz.magnum.app.ui.singleViews.AppTopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SinglePaneView(
    navigator: NavigationViewModel = koinViewModel(),
    titleKey: String,
    content: @Composable () -> Unit,
    navBack : () -> Unit,
    actions: @Composable () -> Unit = {},
) {

    val windowInfo = rememberWindowInfo()

    SideEffect {
        if (windowInfo.screenWidthInfo != WindowInfo.WindowType.Compact) {
            navigator.navigateByIndex()
        }
    }



    Scaffold(
        topBar = { AppTopBar(title = titleKey, navBack = { navBack() }, actions = { actions() }) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                content()
            }
        }
    )
}