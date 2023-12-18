package kz.magnum.app.ui.components.productsDialog

import aab.lib.commons.ui.animations.ShimmerBox
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kz.magnum.app.R
import kz.magnum.app.ui.builders.baseViews.PagedListView
import kz.magnum.app.ui.singleViews.AppCardBox
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun PartnerProductsList(viewModel: PartnerProductViewModel = koinViewModel()) {
    PagedListView(
        viewModel = viewModel,
        loadingView = {
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            )
        },
        listItem = {
            AppCardBox(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Circle,
                        contentDescription = "circle",
                        modifier = Modifier.size(8.dp),
                        tint = Primary
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = it.name, style = Typography.bodyMedium)
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.barcode),
                                contentDescription = "barcode",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                            Text(text = it.barCode, style = Typography.bodyMedium)
                        }
                    }
                }
            }
        }
    )
}