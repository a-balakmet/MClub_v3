package kz.magnum.app.ui.components.campaigns.list

import aab.lib.commons.ui.animations.ShimmerBox
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kz.magnum.app.application.extenions.toStringDate
import kz.magnum.app.ui.builders.baseViews.DatabaseListView
import kz.magnum.app.ui.navigation.Destinations.campaignScreen
import kz.magnum.app.ui.singleViews.EmptyView
import kz.magnum.app.ui.singleViews.RotatedBadge
import kz.magnum.app.ui.singleViews.UrlImage
import kz.magnum.app.ui.theme.AppYellowColor
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun CampaignsList(
    viewModel: CampaignsViewModel = koinViewModel(),
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
        topView = { EmptyView() },
        listItem = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier.padding(bottom = 16.dp),
                    shape = Shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    )
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        UrlImage(
                            link = it.squareImage,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(8.dp, 8.dp, 0.dp, 0.dp))
                                .padding(bottom = 12.dp),
                            contentScale = ContentScale.FillWidth
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)
                        ) {
                            Text(text = it.name, style = Typography.bodyLarge)
                            Text(text = it.description, style = Typography.bodyMedium)
                            Text(
                                text = "${it.start.toStringDate(it.start.year != it.stop.year)} - ${it.stop.toStringDate(it.start.year != it.stop.year)}",
                                style = Typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.BottomEnd),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    it.badges?.let { badges ->
                        badges.forEach { text ->
                            RotatedBadge(text = text, textColor = Primary, textStyle = Typography.titleSmall, backgroundColor = AppYellowColor)
                        }
                    }
                }
            }
        },
        onSelect = {
          onSelect(it)
        },
        childName = campaignScreen,
        bottomIndex = 3,
    )
}