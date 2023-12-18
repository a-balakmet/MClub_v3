package kz.magnum.app.ui.components.clubs.list

import aab.lib.commons.ui.animations.ShimmerBox
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.magnum.app.ui.builders.baseViews.DatabaseListView
import kz.magnum.app.ui.navigation.Destinations.clubScreen
import kz.magnum.app.ui.singleViews.AppCardBox
import kz.magnum.app.ui.singleViews.UrlImage
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.AppGreenColor
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun ClubsList(
    viewModel: ClubsViewModel = koinViewModel(),
    onSelect: (Int) -> Unit
) {

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
                    Text(text = "🎉", fontSize = 38.sp)
                    TranslatedText(key = "club_greet_text", style = Typography.titleMedium)
                }
            }
        },
        listItem = {
            AppCardBox(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(62.dp)) {
                        UrlImage(
                            link = it.thumbnailUrl,
                            modifier = Modifier
                                .size(60.dp)
                                .align(Alignment.Center)
                                .clip(shape = Shapes.small),
                            imageHeight = 60.dp,
                            contentScale = ContentScale.FillBounds
                        )
                        if (it.isMember) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "member",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(16.dp)
                                    .drawBehind { drawCircle(color = AppGreenColor, radius = 24F) },
                                tint = Color.White,
                            )
                        }
                    }
                    Text(text = it.name, style = Typography.headlineMedium, modifier = Modifier.padding(start = 24.dp))
                    Spacer(modifier = Modifier.weight(1F))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "open club")
                }
            }
        },
        onSelect = { onSelect(it) },
        childName = clubScreen,
        bottomIndex = 0,
    )
}