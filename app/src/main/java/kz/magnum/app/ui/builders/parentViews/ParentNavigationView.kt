package kz.magnum.app.ui.builders.parentViews

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kz.magnum.app.ui.navigation.NavigationViewModel
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.RegularFont
import org.koin.androidx.compose.koinViewModel

@Composable
fun ParentNavigationView(
    navigator: NavigationViewModel = koinViewModel(),
    topBar: @Composable () -> Unit = {},
    leftPane: @Composable () -> Unit = {},
    rightPane: @Composable () -> Unit = {},
) {

    val windowInfo = rememberWindowInfo()
    val isTablet by remember { mutableStateOf(windowInfo.screenWidthInfo != WindowInfo.WindowType.Compact) }

    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.isStatusBarVisible = true
    systemUiController.isSystemBarsVisible = true
    systemUiController.setStatusBarColor(color = MaterialTheme.colorScheme.surfaceVariant)

    LaunchedEffect(isTablet) {
        if (!isTablet) {
            if (navigator.bottomIndex > 4) {
                navigator.navigateByParents(navigator.phoneNavItems.first { it.id == 0 })
            }
        }
    }

    // Navigation by parent screens for tablets and foldables
    @Composable
    fun tabletNavigationColumn(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxHeight()
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                navigator.tabletNavItems.forEach { item ->
                    IconButton(onClick = {
                        navigator.navigateByParents(item)
                    }) {
                        if (item.isIcon) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.name,
                                tint = if (item.id == navigator.bottomIndex) Primary else MaterialTheme.colorScheme.onBackground
                            )
                        } else {
                            Image(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.name,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = { topBar() },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                // contents
                when (windowInfo.screenWidthInfo) {
                    WindowInfo.WindowType.Compact -> {
                        leftPane()
                    }

                    else -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Row(modifier = Modifier.fillMaxHeight()) {
                                Box(
                                    modifier = Modifier
                                        .width(64.dp)
                                        .align(Alignment.CenterVertically)
                                        .background(
                                            color = MaterialTheme.colorScheme.tertiaryContainer,
                                            shape = RoundedCornerShape(
                                                bottomEnd = 16.dp,
                                                topEnd = 16.dp,
                                            )
                                        )
                                ) {
                                    tabletNavigationColumn(modifier = Modifier.width(64.dp))
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1F)
                                ) {
                                    leftPane()
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1F)
                                ) {
                                    rightPane()
                                }
                            }
                        }
                    }
                }
                if (!isTablet) {
                    // Navigation by parent screens for phones
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                shape = RoundedCornerShape(
                                    topStart = 16.dp,
                                    topEnd = 16.dp,
                                )
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 1.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp,
                                    )
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .padding(horizontal = 8.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                (navigator.phoneNavItems).forEach { item ->
                                    Column(
                                        modifier = Modifier.fillMaxHeight(),
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        IconButton(onClick = {
                                            navigator.navigateByParents(item)
                                        }) {
                                            if (item.isIcon) {
                                                Icon(
                                                    painter = painterResource(id = item.icon),
                                                    contentDescription = item.name,
                                                    tint = if (item.id == navigator.bottomIndex) Primary else MaterialTheme.colorScheme.onBackground
                                                )
                                            } else {
                                                Image(
                                                    painter = painterResource(id = item.icon),
                                                    contentDescription = item.name,
                                                    contentScale = ContentScale.Fit,
                                                    modifier = Modifier.size(32.dp)
                                                )
                                            }
                                        }
                                        TranslatedText(
                                            key = item.name,
                                            style = TextStyle(fontFamily = RegularFont, fontSize = 10.sp),
                                            color = if (navigator.bottomIndex == item.id) Primary else MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    )
}