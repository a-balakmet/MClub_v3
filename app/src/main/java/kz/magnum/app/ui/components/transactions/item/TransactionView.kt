package kz.magnum.app.ui.components.transactions.item

import aab.lib.commons.ui.animations.ShimmerBox
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import kz.magnum.app.ui.builders.baseViews.ItemView
import kz.magnum.app.ui.singleViews.badges.ErrorBadge
import kz.magnum.app.ui.singleViews.buttons.EnablingButton
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.AppGreenColor
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionView(viewModel: TransactionViewModel = koinViewModel()) {

    val context = LocalContext.current

    ItemView(
        viewModel = viewModel,
        loading = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ShimmerBox(modifier = Modifier.fillMaxWidth().height(16.dp))
                    ShimmerBox(modifier = Modifier.fillMaxWidth().height(20.dp))
                }
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ShimmerBox(modifier = Modifier.fillMaxWidth().height(16.dp))
                    ShimmerBox(modifier = Modifier.fillMaxWidth().height(16.dp))
                    ShimmerBox(modifier = Modifier.fillMaxWidth().height(14.dp))
                }
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ShimmerBox(modifier = Modifier.fillMaxWidth().height(16.dp))
                    ShimmerBox(modifier = Modifier.fillMaxWidth().height(16.dp))
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceTint)
            }
        },
        result = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Date and title
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = it.transaction.dateTime.toLocalisedDate(viewModel.locale), style = Typography.titleMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        TranslatedText(key = it.transaction.name, style = Typography.displaySmall)
                        if (it.transaction.name == "checkAmountLong") {
                            Text(text = it.transaction.sum, style = Typography.displaySmall)
                        }
                    }
                }
                when {
                    // Types of transaction
                    it.transaction.name == "support" -> {
                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(text = "Magnum Club", style = Typography.titleLarge, color = Primary)
                            TranslatedText(key = if (it.transaction.withdrawal == "") "manual_add" else "manual_withdraw", style = Typography.headlineSmall)
                            TranslatedText(key = "transaction_about", style = Typography.bodyMedium)
                        }
                    }

                    it.transaction.name == "parking_refund" -> {
                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(text = "Magnum Club", style = Typography.titleLarge, color = Primary)
                            TranslatedText(key = "thanks", style = Typography.headlineSmall)
                            TranslatedText(key = "thanks_for_shop", style = Typography.bodyMedium)
                        }
                    }

                    it.transaction.name.contains("refer") -> {
                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(text = "Magnum Club", style = Typography.titleLarge, color = Primary)
                            TranslatedText(key = if (it.transaction.name == "referee") "welcome" else "thanks", style = Typography.headlineSmall)
                            TranslatedText(key = "${it.transaction.name}_invite", style = Typography.bodyMedium)
                        }
                    }

                    it.transaction.name.contains("partner") -> {
                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(text = "Magnum Club", style = Typography.titleLarge, color = Primary)
                            TranslatedText(key = "manual_add", style = Typography.headlineSmall)
                            TranslatedText(key = "partner_bonuses_charged", style = Typography.bodyMedium)
                        }
                    }

                    else -> {
                        // Shop data
                        it.shop?.let { shop ->
                            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(text = "Magnum ${shop.type}", style = Typography.titleLarge, color = Primary)
                                Text(text = shop.name, style = Typography.headlineSmall)
                                Text(text = shop.address, style = Typography.bodyMedium)
                            }
                        }
                    }
                }
                // Acquired bonuses and withdrawals
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (it.transaction.bonus != "")
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            TranslatedText(key = "charged", style = Typography.titleLarge)
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(text = it.transaction.bonus, style = Typography.titleLarge, color = AppGreenColor)
                                Icon(
                                    painter = painterResource(id = R.drawable.bonus),
                                    contentDescription = "bonus",
                                    modifier = Modifier.size(13.dp),
                                    tint = AppGreenColor
                                )
                            }
                        }
                    if (it.transaction.withdrawal != "") {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            TranslatedText(key = "withdrawn", style = Typography.titleLarge)
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(text = it.transaction.withdrawal, style = Typography.titleLarge, color = Primary)
                                Icon(painter = painterResource(id = R.drawable.bonus), contentDescription = "bonus", modifier = Modifier.size(13.dp), tint = Primary)
                            }
                        }
                    }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceTint)
                // List of goods
                it.goods?.let { goods ->
                    if (goods.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1F)
                        ) {
                            TranslatedText(modifier = Modifier.padding(bottom = 8.dp), key = "receipt_content", style = Typography.displaySmall)
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                goods.forEach { item ->
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                        Text(text = item.name, modifier = Modifier.weight(1F), style = Typography.bodyMedium)
                                        Column(horizontalAlignment = Alignment.End) {
                                            Text(text = item.quantityAndPrice, style = Typography.bodyLarge)
                                            Text(text = item.totalPrice, style = Typography.headlineSmall)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // Link to receipt
                it.transaction.receiptLink?.let { link ->
                    EnablingButton(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(),
                        isEnabled = true,
                        isLoading = false,
                        buttonText = "look_recept"
                    ) {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                            context.startActivity(intent)
                        } catch (s: SecurityException) {
                            Log.d("mClub", "TransactionScreen: $s")
                        }
                    }
                }
            }
        },
        error = {
            ErrorBadge(error = it)
        }
    )
}
