package kz.magnum.app.ui.components.miniGames

import aab.lib.commons.ui.animations.ShimmerBox
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.magnum.app.domain.models.ListUIEvent
import kz.magnum.app.ui.builders.baseViews.DatabaseListView
import kz.magnum.app.ui.navigation.Destinations.campaignScreen
import kz.magnum.app.ui.singleViews.AppCardBox
import kz.magnum.app.ui.singleViews.UrlImage
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun MiniGamesList(viewModel: MiniGamesViewModel = koinViewModel()) {

    val context = LocalContext.current

    DatabaseListView(
        viewModel = viewModel,
        loading = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                for (i in 0..2) {
                    Card(
                        shape = Shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    ) {
                        ShimmerBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(224.dp)
                        )
                    }
                }
            }
        },
        topView = {
            AppCardBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = "🎮", fontSize = 38.sp)
                    //TranslatedText(key = "mini_gamed_description", style = Typography.titleMedium)
                    Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.", style = Typography.titleMedium)
                }
            }
        },
        listItem = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
                            viewModel.onEvent(event = ListUIEvent.OpenLink(context, it.gameUrl))
                        },
                    shape = Shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp)
                    ) {
                        UrlImage(
                            link = it.imageUrl,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(shape = Shapes.medium),
                            contentScale = ContentScale.FillBounds
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .padding(start = 16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = it.name, style = Typography.headlineMedium)
                            Box(
                                modifier = Modifier
                                    .background(color = Primary, shape = Shapes.medium)
                            ) {
                                TranslatedText(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp, horizontal = 16.dp),
                                    key = "lets_play",
                                    style = Typography.bodyLarge,
                                    color = Color.White
                                )
                            }

                        }
                    }
                }
            }
        },
        onSelect = {},
        childName = campaignScreen,
        bottomIndex = 3,
    )
}