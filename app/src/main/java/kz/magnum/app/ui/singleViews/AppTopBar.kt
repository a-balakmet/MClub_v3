package kz.magnum.app.ui.singleViews

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kz.magnum.app.ui.navigation.Destinations.miniGamesScreen
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    isParent: Boolean = false,
    isMainScreen: Boolean = false,
    title: String,
    navBack: () -> Unit,
    actions: @Composable () -> Unit = {},
) {
    if (isMainScreen) {
        TopAppBar(
            title = { Text(title, style = Typography.displaySmall) },
            modifier = Modifier.shadow(elevation = 8.dp, spotColor = MaterialTheme.colorScheme.onBackground),
            navigationIcon = {
                if (!isParent) {
                    IconButton(onClick = { navBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "go back",
                        )
                    }
                }
            },
            actions = { actions() },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    } else {
        CenterAlignedTopAppBar(
            title = {
                when (title) {
                    miniGamesScreen -> Text(text = "Mini Games", style = Typography.displaySmall, modifier = Modifier)
                    "transactions_title" -> TranslatedText(key = "transactions_bonuses", style = Typography.displaySmall, modifier = Modifier)
                    else -> TranslatedText(key = title, style = Typography.displaySmall, modifier = Modifier)
                }
            },
            modifier = Modifier.shadow(elevation = 8.dp, spotColor = MaterialTheme.colorScheme.onBackground),
            navigationIcon = {
                if (!isParent) {
                    IconButton(onClick = { navBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "go back",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            actions = { actions() },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}