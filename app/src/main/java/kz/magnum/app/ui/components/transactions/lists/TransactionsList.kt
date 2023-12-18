package kz.magnum.app.ui.components.transactions.lists

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.animations.ShimmerBox
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import kz.magnum.app.application.extenions.isWithinRange
import kz.magnum.app.application.extenions.localisedMonthName
import kz.magnum.app.application.extenions.shortWeekDay
import kz.magnum.app.application.extenions.toLocalisedDate
import kz.magnum.app.application.extenions.toShortLocalisedDate
import kz.magnum.app.domain.models.ListUIEvent
import kz.magnum.app.domain.models.MonthAndDays
import kz.magnum.app.ui.builders.baseViews.PagedListView
import kz.magnum.app.ui.navigation.Destinations.transactionScreen
import kz.magnum.app.ui.singleViews.AppCardBox
import kz.magnum.app.ui.singleViews.buttons.EnablingButton
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.AppGreenColor
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsList(
    viewModel: TransactionsViewModel = koinViewModel(),
    onSelect: (Int) -> Unit
) {

    val start = viewModel.start.collectAsStateWithLifecycle()
    val end = viewModel.end.collectAsStateWithLifecycle()

    val changeableStart = mutableStateOf<LocalDateTime?>(start.value)
    val changeableEnd = mutableStateOf<LocalDateTime?>(end.value)

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.PartiallyExpanded }
    )
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val windowInfo = rememberWindowInfo()
    val isTablet by remember { mutableStateOf(windowInfo.screenWidthInfo != WindowInfo.WindowType.Compact) }

    val interactionSource = remember { MutableInteractionSource() }

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    SideEffect {
        coroutineScope.launch {
            if (sheetState.isVisible) scrollState.scrollTo(scrollState.maxValue)
        }
    }

    fun MonthAndDays.toDateTime(isFirstDay: Boolean) =
        if (isFirstDay) LocalDateTime.of(this.month.year, this.month.month, 1, 0, 0, 0)
        else LocalDateTime.of(this.month.year, this.month.month, this.month.lengthOfMonth(), 23, 59, 59)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppCardBox(
            modifier = Modifier
                .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)
                .height(48.dp)
                .clickable(interactionSource = interactionSource, indication = null) {
                    coroutineScope.launch { sheetState.expand() }
                }
        ) {
            Row(modifier = Modifier.padding(all = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Row {
                    Text(
                        text = start.value.toShortLocalisedDate(viewModel.locale),
                        style = Typography.bodyLarge
                    )
                    Text(
                        text = ", ${start.value.shortWeekDay(viewModel.locale)}",
                        style = Typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onTertiaryContainer)
                    )
                }

                VerticalDivider(color = MaterialTheme.colorScheme.surfaceTint)
                Row {
                    Text(
                        text = end.value.toShortLocalisedDate(viewModel.locale),
                        style = Typography.bodyLarge
                    )
                    Text(
                        text = ", ${end.value.shortWeekDay(viewModel.locale)}",
                        style = Typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onTertiaryContainer)
                    )
                }
            }
        }
        HorizontalDivider(modifier = Modifier.height(24.dp), color = MaterialTheme.colorScheme.surfaceTint)
        PagedListView(
            viewModel = viewModel,
            loadingView = {
                ShimmerBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(32.dp)
                )
            },
            listItem = {
                AppCardBox(modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        if (it.name != "burning_bonus") {
                            onSelect(it.id)
                            viewModel.onEvent(event = ListUIEvent.SelectAndNavigate(it, transactionScreen, if (isTablet) 1 else 0, !isTablet))
                        }
                    }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(26.dp)
                                .background(color = Primary, shape = CircleShape)
                        ) {
                            Icon(
                                painter = painterResource(id = it.icon),
                                contentDescription = it.name,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(16.dp),
                                tint = Color.White
                            )
                        }
                        Column(modifier = Modifier.padding(horizontal = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                TranslatedText(key = it.name, style = Typography.bodyLarge)
                                if (it.name == "checkAmountLong") Text(text = it.sum, style = Typography.bodyLarge)
                            }
                            Text(text = it.dateTime.toLocalisedDate(viewModel.locale), style = Typography.bodyLarge)
                        }
                        Spacer(modifier = Modifier.weight(1F))
                        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            if (it.name != "burning_bonus") {
                                Text(text = it.bonus, color = AppGreenColor, style = Typography.bodyLarge)
                                Text(text = it.withdrawal, color = Color.Red, style = Typography.bodyLarge)
                            } else {
                                Text(text = it.withdrawal, style = Typography.bodyLarge)
                                Text(text = "", style = Typography.bodyLarge)
                            }
                        }
                    }
                }
            }
        )
    }

    // Bottom dialog
    if (sheetState.isVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                coroutineScope.launch { sheetState.hide() }
            },
            modifier = Modifier.height(windowInfo.screenHeight - 64.dp),
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 64.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    TranslatedText(key = "date_choice", style = Typography.headlineLarge)
                    TranslatedText(
                        modifier = Modifier.clickable(interactionSource = interactionSource, indication = null) {
                            coroutineScope.launch { sheetState.hide() }
                            viewModel.onRefresh()
                            viewModel.updateDates(LocalDateTime.now().minusMonths(1), LocalDateTime.now())
                        },
                        key = "cancel_sorting",
                        style = Typography.titleLarge,
                        color = Primary
                    )
                }
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .weight(1F),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    viewModel.totalDates.forEach { monthAndYear ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = monthAndYear.month.localisedMonthName(Locale(viewModel.locale), true),
                                        style = Typography.headlineLarge
                                    )
                                    Text(text = "${monthAndYear.month.year}", style = Typography.bodyLarge, color = MaterialTheme.colorScheme.onTertiaryContainer)
                                }
                                TranslatedText(
                                    modifier = Modifier.clickable(interactionSource = interactionSource, indication = null) {
                                        coroutineScope.launch { sheetState.hide() }
                                        viewModel.updateDates(monthAndYear.toDateTime(true), monthAndYear.toDateTime(false))
                                    },
                                    key = "choose_month",
                                    style = Typography.bodySmall,
                                    color = Primary
                                )
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                viewModel.weekDays.map {
                                    Box(
                                        modifier = Modifier
                                            .width(40.dp)
                                            .padding(vertical = 16.dp)
                                    ) {
                                        Text(
                                            text = it.getDisplayName(TextStyle.SHORT, Locale(viewModel.locale)),
                                            modifier = Modifier.align(Alignment.Center),
                                            style = Typography.bodyLarge
                                        )
                                    }
                                }
                            }
                            monthAndYear.days.forEach {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    it.map {
                                        Box(
                                            modifier = Modifier
                                                .height(40.dp)
                                                .weight(1F)
                                                .background(
                                                    color = if (it != null) {
                                                        when {
                                                            changeableStart.value != null && changeableEnd.value != null -> {
                                                                if (it.isWithinRange(changeableStart.value!!.toLocalDate(), changeableEnd.value!!.toLocalDate())
                                                                    || it == changeableStart.value!!.toLocalDate()
                                                                    || it == changeableEnd.value!!.toLocalDate()
                                                                ) Primary.copy(alpha = 0.06F)
                                                                else Color.Transparent
                                                            }

                                                            else -> Color.Transparent
                                                        }
                                                    } else Color.Transparent,
                                                    shape = when (it) {
                                                        changeableStart.value?.toLocalDate() -> RoundedCornerShape(
                                                            topStart = 8.dp,
                                                            topEnd = 0.dp,
                                                            bottomEnd = 0.dp,
                                                            bottomStart = 8.dp
                                                        )

                                                        changeableEnd.value?.toLocalDate() -> RoundedCornerShape(
                                                            topStart = 0.dp,
                                                            topEnd = 8.dp,
                                                            bottomEnd = 8.dp,
                                                            bottomStart = 0.dp
                                                        )

                                                        else -> RoundedCornerShape(0.dp)
                                                    }
                                                )
                                        ) {
                                            if (it != null) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(32.dp)
                                                        .align(Alignment.Center)
                                                        .background(
                                                            color =
                                                            if (it == changeableStart.value?.toLocalDate() || it == changeableEnd.value?.toLocalDate()) Primary
                                                            else Color.Transparent,
                                                            shape = RoundedCornerShape(8.dp)
                                                        )
                                                        .clickable(interactionSource = interactionSource, indication = null) {
                                                            if (!it.isAfter(LocalDate.now())) {
                                                                val aDay = it
                                                                when {
                                                                    changeableStart.value != null && changeableEnd.value != null -> {
                                                                        changeableStart.value = it.atStartOfDay()
                                                                        changeableEnd.value = null
                                                                    }

                                                                    changeableStart.value != null && changeableEnd.value == null -> {
                                                                        if (aDay.isAfter(changeableStart.value!!.toLocalDate())) {
                                                                            changeableEnd.value = aDay.atStartOfDay()
                                                                        } else {
                                                                            changeableEnd.value = changeableStart.value
                                                                            changeableStart.value = aDay.atStartOfDay()
                                                                        }

                                                                    }
                                                                }
                                                            }
                                                        }
                                                ) {
                                                    Text(
                                                        text = "${it.dayOfMonth}",
                                                        modifier = Modifier.align(Alignment.Center),
                                                        style = Typography.titleMedium,
                                                        color = if (it == changeableStart.value?.toLocalDate() || it == changeableEnd.value?.toLocalDate()) Color.White
                                                        else if (it.isAfter(LocalDate.now())) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F)
                                                        else MaterialTheme.colorScheme.onSurface
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                EnablingButton(
                    modifier = Modifier
                        .size(width = (if (isTablet) windowInfo.screenWidth / 2 else windowInfo.screenWidth) - 32.dp, height = 50.dp)
                        .align(Alignment.CenterHorizontally),
                    isEnabled = true,
                    isLoading = false,
                    buttonText = "choose"
                ) {
                    coroutineScope.launch { sheetState.hide() }
                    if (changeableStart.value != null && changeableEnd.value != null) {
                        viewModel.updateDates(changeableStart.value!!, changeableEnd.value!!)
                    }
                }
            }
        }
    }
}