package kz.magnum.app.ui.singleViews.profileViews

import aab.libs.datetimewheel.core.WheelPickerDefaults
import aab.libs.datetimewheel.views.WheelDatePicker
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kz.magnum.app.application.extenions.toBackEndPattern
import kz.magnum.app.application.extenions.toBirthday
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdaySelector(
    title: String,
    hint: String,
    screenWidth: Dp,
    locale: String,
    onDateSelected: (String) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.PartiallyExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    Column(modifier = Modifier
        .padding(bottom = 16.dp)
        .clickable(
            indication = null,
            interactionSource = interactionSource
        ) {
            coroutineScope.launch {
                sheetState.hide()
            }
        }, verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(title, style = Typography.bodyMedium, modifier = Modifier.padding(start = 16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = MaterialTheme.colorScheme.surface, shape = Shapes.medium)
                .border(width = 1.dp, color = MaterialTheme.colorScheme.outline , shape = Shapes.medium)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    coroutineScope.launch { sheetState.expand() }
                }
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (selectedDate == null) {
                    Text(text = hint, style = Typography.titleLarge, color = MaterialTheme.colorScheme.outline)
                } else {
                    Text(text = selectedDate!!.toBirthday(), style = Typography.titleLarge)
                }
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "open calendar")
            }
        }
        // Bottom dialog
        if (sheetState.isVisible) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    coroutineScope.launch { sheetState.hide() }
                },
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                ) {
                    WheelDatePicker(
                        modifier = Modifier.padding(horizontal = 8.dp).align(Alignment.CenterHorizontally),
                        startDate = LocalDate.now().minusYears(14),
                        maxDate = LocalDate.now().minusYears(14),
                        yearsRange = IntRange(1900, LocalDate.now().minusYears(14).year),
                        size = DpSize(screenWidth, 256.dp),
                        rowCount = 7,
                        textStyle = Typography.headlineSmall,
                        selectorProperties = WheelPickerDefaults.selectorProperties(shape = Shapes.medium),
                        locale = locale
                    ) {
                        selectedDate = it
                        onDateSelected(it.atStartOfDay().toBackEndPattern())
                        //onDateSelected(it.atTime(13,  13, 13).toBackEndPattern())
                    }
                }
            }
        }
    }
}