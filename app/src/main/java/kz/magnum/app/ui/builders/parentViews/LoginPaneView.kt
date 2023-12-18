package kz.magnum.app.ui.builders.parentViews

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPaneView(
    showBackArrow: Boolean = true,
    onBackPressed: () -> Unit,
    titleText: String,
    scrollableContent: Boolean = false,
    content: @Composable () -> Unit = {},
) {

    val windowInfo = rememberWindowInfo()
    val isTablet = remember { mutableStateOf(windowInfo.screenWidthInfo != WindowInfo.WindowType.Compact) }
//    val configuration = LocalConfiguration.current
//    val screenWidth = configuration.screenWidthDp.dp
//    val screenHeight = configuration.screenHeightDp.dp

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    if (showBackArrow) {
                        IconButton(onClick = {
                            onBackPressed()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "Back",
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                if (scrollableContent) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(if (isTablet.value)  windowInfo.screenWidth / 2 else windowInfo.screenWidth - 32.dp)
                            .align(Alignment.Center)
                            .verticalScroll(rememberScrollState())
                    ) {
                        TranslatedText(
                            modifier = Modifier.padding(bottom = 16.dp),
                            key = titleText,
                            style = if (isTablet.value) Typography.displayLarge else Typography.displayMedium
                        )
                        content()
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(if (isTablet.value) windowInfo.screenWidth / 2 else windowInfo.screenWidth - 32.dp)
                            .align(Alignment.Center)
                    ) {
                        TranslatedText(modifier = Modifier.padding(bottom = 16.dp), key = titleText, style = Typography.displayLarge)
                        content()
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    )
}