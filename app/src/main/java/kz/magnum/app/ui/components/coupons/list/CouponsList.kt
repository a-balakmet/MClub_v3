package kz.magnum.app.ui.components.coupons.list

import aab.lib.commons.ui.animations.ShimmerBox
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import kz.magnum.app.application.extenions.toStringDate
import kz.magnum.app.domain.models.Coupon
import kz.magnum.app.ui.builders.baseViews.TwoPanesListView
import kz.magnum.app.ui.navigation.Destinations.couponScreen
import kz.magnum.app.ui.singleViews.EmptyView
import kz.magnum.app.ui.singleViews.UrlImage
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun CouponsList(viewModel: CouponsListViewModel = koinViewModel(), onSelect: (Coupon) -> Unit) {

    TwoPanesListView(
        viewModel = viewModel,
        loading = {

            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(all = 16.dp), shape = Shapes.medium
            )
        },
        topView = { EmptyView() },
        listItem = { coupon ->
            if (coupon.stateInt < 3) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(166.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(color = colorResource(id = coupon.couponColor), shape = Shapes.medium)
                    ) {
                        UrlImage(
                            link = coupon.imageLink,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(shape = Shapes.medium),
                            contentScale = ContentScale.FillBounds
                        )
                        Column(
                            modifier = Modifier.padding(all = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = coupon.name, style = Typography.displaySmall, color = Color.White)
                            Text(text = coupon.value, style = Typography.titleLarge, color = Color.White)
                            Spacer(modifier = Modifier.weight(1F))
                            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                TranslatedText(key = "active_coupon_2", style = Typography.bodyLarge, color = Color.White)
                                Text(text = coupon.dateTo.toStringDate(showYear = true), style = Typography.bodyLarge, color = Color.White)
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(color = colorResource(id = coupon.couponColor), shape = Shapes.small)
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = Shapes.small
                                )
                        ) {
                            TranslatedText(
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                                key = coupon.stateString,
                                style = Typography.titleLarge,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        },
        onSelect = { onSelect(it) },
        childName = couponScreen,
    )
}