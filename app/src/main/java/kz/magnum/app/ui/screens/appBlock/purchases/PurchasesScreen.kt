package kz.magnum.app.ui.screens.appBlock.purchases

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kz.magnum.app.ui.builders.parentViews.ParentNavigationView
import kz.magnum.app.ui.singleViews.AppTopBar

@Composable
fun PurchasesScreen(){

    ParentNavigationView(
        topBar = {
            AppTopBar(isParent = true, navBack = {}, title = "shop_list") {}
        },
        leftPane = {
            Box {
                Button(onClick = {  }) {
                    Text(text = "left pane of list of purchases - UNDER CONSTRUCTION")
                }

            }
        },
        rightPane = {
            Text(text = "this is right pane - UNDER CONSTRUCTION")
        },
    )
}