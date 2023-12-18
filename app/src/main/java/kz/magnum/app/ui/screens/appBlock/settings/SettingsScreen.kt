package kz.magnum.app.ui.screens.appBlock.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kz.magnum.app.ui.builders.parentViews.ParentNavigationView
import kz.magnum.app.ui.singleViews.AppTopBar

@Composable
fun SettingsScreen(){

    ParentNavigationView(
        topBar = {
            AppTopBar(isParent = true, navBack = {}, title = "settings") {}
        },
        leftPane = {
            Box {
                Button(onClick = {  }) {
                    Text(text = "left pane of settings - UNDER CONSTRUCTION")
                }

            }
        },
        rightPane = {
            Text(text = "this is right pane - UNDER CONSTRUCTION")
        },
    )
}