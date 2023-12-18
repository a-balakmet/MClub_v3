package kz.magnum.app.ui.components.transactions.lists

import aab.lib.commons.ui.animations.ShimmerBox
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kz.magnum.app.R
import kz.magnum.app.application.extenions.toLocalisedDate
import kz.magnum.app.ui.builders.baseViews.SinglePaneListView
import kz.magnum.app.ui.singleViews.AppCardBox
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun BurningsList(viewModel: BurningsListViewModel = koinViewModel()) {

    var isExpanded by remember { mutableStateOf(false) }

    SinglePaneListView(
        viewModel = viewModel,
        loading = {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                for (i in 0..2) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ShimmerBox(
                                modifier = Modifier
                                    .size(14.dp)
                                    .padding(end = 2.dp)
                            )
                            ShimmerBox(
                                modifier = Modifier
                                    .height(14.dp)
                                    .width(80.dp)
                            )
                            Spacer(modifier = Modifier.weight(1F))
                            ShimmerBox(
                                modifier = Modifier
                                    .height(18.dp)
                                    .width(32.dp)
                            )
                        }
                        if (i < 2) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                color = MaterialTheme.colorScheme.surfaceTint
                            )
                        }
                    }
                }
            }
        },
        topView = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppCardBox(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(color = Primary, shape = CircleShape)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.burning),
                                contentDescription = "burning icon", tint = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Column(modifier = Modifier.weight(1F), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            TranslatedText(key = "burning_term", style = Typography.bodyMedium)
                            if (isExpanded) {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    TranslatedText(key = "burning_term_second", style = Typography.bodyMedium)
                                    TranslatedText(key = "burning_term_third", style = Typography.bodyMedium)
                                }
                            }
                            Row(
                                modifier = Modifier.clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { isExpanded = !isExpanded },
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TranslatedText(key = "in_details", style = Typography.bodyMedium, color = Primary)
                                Icon(
                                    imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "reveal details",
                                    tint = Primary
                                )
                            }
                        }
                    }
                }
                TranslatedText(key = "burn_schedule", style = Typography.headlineLarge)
            }
        },
        listItem = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = it.name.toLocalisedDate(viewModel.locale), style = Typography.bodyMedium)
                    Text(text = "${it.id}", style = Typography.headlineMedium)
                }
                viewModel.state.value.result?.let { burnings ->
                    if (burnings.last() != it) {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            color = MaterialTheme.colorScheme.surfaceTint
                        )
                    }
                }
            }
        },
        noDataMessage = null
    )
}