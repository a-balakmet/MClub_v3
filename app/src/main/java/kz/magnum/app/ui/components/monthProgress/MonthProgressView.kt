package kz.magnum.app.ui.components.monthProgress

import aab.lib.commons.ui.animations.ShimmerBox
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kz.magnum.app.ui.singleViews.AppCardBox
import kz.magnum.app.ui.singleViews.texts.ErrorText
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.AppGreenColor
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MonthProgressView(
    viewModel: MonthProgressViewModel = koinViewModel(),
    showNext: Boolean,
    onClickNext: () -> Unit
) {

    val state = viewModel.state.value
    val ruLocale = Locale("ru", "RU")
    val digitsFormatter = DecimalFormat("#,###", DecimalFormatSymbols(ruLocale))

    AppCardBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Current sum and month
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                // current sum
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    TranslatedText(key = "monthly_purchases", style = Typography.bodyLarge)
                    if (state.isLoading) {
                        ShimmerBox(
                            modifier = Modifier
                                .width(48.dp)
                                .height(14.dp)
                        )
                    } else if (state.result != null) {
                        Text(text = "${digitsFormatter.format(state.result!!.totalSum)} ₸", style = Typography.bodyLarge)
                    } else {
                        Text(text = "0 ₸", style = Typography.bodyLarge, color = Primary)
                    }
                }
                // current month
                Text(
                    text = SimpleDateFormat("LLLL", Locale(viewModel.locale)).format(Date())
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                    style = Typography.bodyLarge, color = Primary
                )
            }
            // Short info
            TranslatedText(key = "min_percent", style = Typography.bodySmall)
            // Heavy progress bar
            if (state.isLoading) {
                ShimmerBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp)
                )
            } else if (state.result != null && state.result!!.isEnabled) {
                if (state.result!!.totalSum < state.result!!.cases.last().amount) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        LinearProgressIndicator(
                            progress = { state.result!!.totalSum.toFloat() / state.result!!.cases.last().amount },
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)
                                .height(11.dp),
                            color = Primary,
                            trackColor = MaterialTheme.colorScheme.surfaceTint
                        )
                        Row(modifier = Modifier.fillMaxWidth()) {
                            state.result!!.cases.forEach { case ->
                                Column(
                                    modifier = Modifier.weight(1F),
                                    horizontalAlignment = when (state.result!!.cases.indexOf(case)) {
                                        0 -> Alignment.Start
                                        state.result!!.cases.size - 1 -> Alignment.End
                                        else -> Alignment.CenterHorizontally
                                    }, verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(text = "${case.percent}%", style = Typography.bodySmall)
                                    Box(
                                        modifier = Modifier
                                            .size(18.dp)
                                            .background(color = MaterialTheme.colorScheme.surface, shape = CircleShape)
                                            .border(
                                                width = 1.dp,
                                                color = MaterialTheme.colorScheme.surfaceTint,
                                                shape = CircleShape
                                            )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "check",
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .size(8.dp),
                                            tint = if (state.result!!.totalSum >= case.amount) Primary else Color.Transparent
                                        )
                                    }
                                    Text(text = "${digitsFormatter.format(case.amount)} ₸", style = Typography.bodySmall)
                                }
                            }
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(43.dp)
                                .background(color = AppGreenColor, shape = CircleShape)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.surfaceTint,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "check",
                                modifier = Modifier
                                    .align(Alignment.Center),
                                tint = Color.White
                            )
                        }
                        TranslatedText(key = "premium_bonus_achieved", style = Typography.bodyMedium)
                    }
                }
            } else if (state.error != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp)
                ) {
                    ErrorText(error = state.error!!)
                }
            }
            // Text and button
            if (state.result != null && state.result!!.isEnabled) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                    TranslatedText(key = "premium_bonus_desc", style = Typography.bodySmall, modifier = Modifier.weight(1F))
                    TranslatedText(key = "in_details", style = Typography.bodyLarge, color = if (showNext) Primary else Color.Transparent, modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { if (showNext) onClickNext() })
                }
            }
        }
    }
}