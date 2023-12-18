package kz.magnum.app.ui.components.messages.list

import aab.lib.commons.extensions.StringConvert
import aab.lib.commons.extensions.StringConvert.toDateTime
import aab.lib.commons.extensions.twoDigitsString
import aab.lib.commons.ui.animations.ShimmerBox
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kz.magnum.app.R
import kz.magnum.app.application.extenions.toLocalisedDate
import kz.magnum.app.domain.models.ListUIEvent
import kz.magnum.app.domain.models.Message
import kz.magnum.app.ui.singleViews.badges.ErrorBadge
import kz.magnum.app.ui.singleViews.badges.NoDataBadge
import kz.magnum.app.ui.singleViews.AppCardBox
import kz.magnum.app.ui.singleViews.UrlImage
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessagesList(viewModel: MessagesListViewModel = koinViewModel()) {

    val state = viewModel.state.value
    val context = LocalContext.current

    @Composable
    fun preLoader() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            ShimmerBox(modifier = Modifier.size(36.dp), shape = CircleShape)
            Box(
                modifier = Modifier
                    .weight(1F)
                    .background(color = MaterialTheme.colorScheme.surface, shape = Shapes.medium)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = Shapes.medium
                    )
            ) {
                Column(
                    modifier = Modifier.padding(all = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp), shape = Shapes.small
                    )
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp), shape = Shapes.small
                    )
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp), shape = Shapes.small
                    )
                }
            }
        }
    }

    @Composable
    fun listItem(item: Message) {
        val dateTime = item.dateCreated.substring(0, 19).toDateTime(StringConvert.dateTimeTFormatter)
        Row(
            modifier = Modifier .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color = MaterialTheme.colorScheme.surface, shape = CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.surfaceTint,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.mini_icon),
                    contentDescription = "icon",
                    modifier = Modifier.align(Alignment.Center),
                    tint = Primary
                )
            }
            AppCardBox(modifier = Modifier.weight(1F)) {
                Column(
                    modifier = Modifier.padding(all = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item.imageLink?.let { link ->
                        if (link.contains("http")) {
                            UrlImage(
                                link = link, modifier = Modifier
                                    .fillMaxWidth()
                                    .height(64.dp)
                            )
                        }
                    }
                    Text(text = item.name, style = Typography.headlineSmall)
                    Text(text = item.message, style = Typography.bodyMedium)
                    if (item.button != null && item.androidLink != null) {
                        Button(
                            onClick = {
                                viewModel.onEvent(event = ListUIEvent.OpenLink(context, item.androidLink))
//                                try {
//                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.payload.androidLink))
//                                    context.startActivity(intent)
//                                } catch (e: Exception) {
//                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://magnum.kz/"))
//                                    context.startActivity(intent)
//                                }
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = Shapes.medium,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = Primary,
                            )
                        ) {
                            Text(
                                item.button,
                                style = Typography.titleLarge,
                                color = Primary,
                            )
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Text(text = "${dateTime.hour.twoDigitsString()}:${dateTime.minute.twoDigitsString()}", style = Typography.bodyMedium)
                    }
                }
            }
        }
    }

    if (state.isLoading) {
        preLoader()
    } else if (state.result != null) {
        if (state.result!!.isEmpty()) {
            NoDataBadge(titleText = "no_notifications", messageText = "notification_coming")
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
            ) {
                val groupedList = viewModel.state.value.result!!.groupBy { it.dateCreated }
                groupedList.forEach { (date, notifications) ->
                    stickyHeader {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.background)
                        ) {
                            Text(text = date.toLocalisedDate(viewModel.locale), style = Typography.bodyMedium, modifier = Modifier
                                .padding(top = 16.dp, start = 52.dp)
                                .align(Alignment.Center))
                        }
                    }
                    items(notifications) { item ->
                        listItem(item = item)
                    }
                }

            }
        }
    } else if (state.error != null) {
        ErrorBadge(error = state.error!!)
    } else {
        Box(modifier = Modifier)
    }
}