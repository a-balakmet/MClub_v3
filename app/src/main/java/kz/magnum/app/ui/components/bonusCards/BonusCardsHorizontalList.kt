package kz.magnum.app.ui.components.bonusCards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kz.magnum.app.R
import kz.magnum.app.application.BarcodeGenerator
import kz.magnum.app.application.extenions.toChunked
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BonusCardsHorizontalList(
    viewModel: BonusCardsViewModel = koinViewModel()
) {

    val cards = viewModel.list.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { cards.value.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
    ) { page ->
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // Image to show card's type with balance
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = if (cards.value[page].type == 1) R.drawable.normal_thin_color else R.drawable.opt_thin_color),
                        contentDescription = "thin card image",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterStart),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TranslatedText(key = "my_bonuses_line", style = Typography.headlineSmall, modifier = Modifier, color = Color.White)
                    Text(text = cards.value[page].balance, style = Typography.headlineSmall, color = Color.White)
                    Icon(painter = painterResource(id = R.drawable.bonus), contentDescription = "bonus sign", modifier = Modifier.size(16.dp), tint = Color.White)
                }
            }
            // Barcode image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp)
                    .padding(horizontal = 16.dp)
                    .background(color = Color.White, shape = Shapes.small)
            ) {
                BarcodeGenerator.generateBonusCardBarcode(cards.value[page].barcode, 48)?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "barcode",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp)
                    )
                }
            }
            // Card's number and state
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = cards.value[page].barcode.toChunked(4), style = Typography.bodySmall)
                TranslatedText(key = if (cards.value[page].isActive) "card_active" else "card_blocked", style = Typography.bodySmall, modifier = Modifier)
            }
        }
    }
}