package kz.magnum.app.ui.components.localeSwitcher

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kz.magnum.app.application.extenions.findActivity
import kz.magnum.app.ui.activity.MainActivity
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun LocaleMenu(viewModel: LocaleViewModel = koinViewModel(), menuColor: Color) {

    val context = LocalContext.current
    val activity = context.findActivity() as MainActivity

    Column {
        Row(modifier = Modifier
            .align(alignment = Alignment.End)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                viewModel.switchMenu()
            }) {
            Text(
                text = if (viewModel.appLocale.value == "ru") "RU" else "KZ",
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                color = menuColor,
                style = Typography.displaySmall
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = if (viewModel.isShowMenu.value) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .align(alignment = Alignment.CenterVertically),
                tint = menuColor
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (viewModel.isShowMenu.value) {
            Card(
                shape = Shapes.medium,
                border = BorderStroke(width = 1.dp, color = Color(0xFFECECEC)),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                )
            ) {
                Column(modifier = Modifier.padding(all = 16.dp)) {
                    Text(
                        text = LocaleEnum.RU.lang,
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                viewModel.setLocale(LocaleEnum.RU.setLocale())
                                activity.viewModel.runLoading(localeChanged = true)
                            },
                        style = Typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = LocaleEnum.KZ.lang,
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                viewModel.setLocale(LocaleEnum.KZ.setLocale())
                                activity.viewModel.runLoading(localeChanged = true)
                            },
                        style = Typography.titleLarge
                    )
                }
            }
        }
    }
}