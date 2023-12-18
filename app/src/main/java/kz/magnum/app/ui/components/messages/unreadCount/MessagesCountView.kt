package kz.magnum.app.ui.components.messages.unreadCount

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.magnum.app.R
import kz.magnum.app.ui.builders.baseViews.ItemView
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.RegularFont
import org.koin.androidx.compose.koinViewModel

@Composable
fun MessagesCountView(
    viewModel: MessagesCountViewModel = koinViewModel(),
    onClick: () -> Unit
) {

    @Composable
    fun icon() {
        IconButton(onClick = { onClick() }) {
            Icon(painter = painterResource(id = R.drawable.messages), contentDescription = "messages")
        }
    }

    ItemView(
        viewModel = viewModel,
        loading = { icon() },
        result = {
            Box(modifier = Modifier) {
                icon()
                if (it.value != 0) {
                    Text(
                        text = "${viewModel.state.value.result?.value ?: ""}",
                        modifier = Modifier.align(Alignment.TopEnd)
                            .padding(all = 8.dp)
                            .drawBehind { drawCircle(color = Primary, radius = 24F) },
                        color = Color.White,
                        style = TextStyle(fontFamily = RegularFont, fontSize = 8.sp),
                    )
                }
            }
        },
        error = { icon() }
    )
}
