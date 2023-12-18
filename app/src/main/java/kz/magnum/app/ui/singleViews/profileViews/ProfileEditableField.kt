package kz.magnum.app.ui.singleViews.profileViews

import aab.lib.commons.extensions.StringConvert.isEmail
import aab.lib.commons.ui.inputs.InputTextField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kz.magnum.app.application.AppConstants.namesRegex
import kz.magnum.app.ui.theme.AppOrangeColor
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Shapes
import kz.magnum.app.ui.theme.Typography

@Composable
fun ProfileEditableField(
    title: String,
    error: String,
    hint: String,
    isEmail: Boolean = false,
    onValueInput: (String) -> Unit
) {

    var editableValue by remember { mutableStateOf("") }

    fun isValueCorrect(): Boolean {
        return if (isEmail) {
            editableValue.isEmail()
        } else {
            namesRegex.matches(editableValue)
        }
    }

    Column(modifier = Modifier.padding(bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (editableValue.isEmpty() || isValueCorrect()) {
            Text(text = title, style = Typography.bodyMedium, modifier = Modifier.padding(start = 16.dp))
        } else {
            Text(text = error, style = Typography.bodyMedium, modifier = Modifier.padding(start = 16.dp), color = AppOrangeColor)
        }
        InputTextField(
            value = editableValue,
            onChange = {
                editableValue = it
                if (editableValue.isEmpty()) {
                    onValueInput("")
                } else {
                    if (isValueCorrect()) {
                        onValueInput(editableValue)
                    } else {
                        onValueInput("")
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isEmail) KeyboardType.Email else KeyboardType.Text,
                capitalization = if (isEmail) KeyboardCapitalization.None else KeyboardCapitalization.Words,
                imeAction = if (isEmail) ImeAction.None else ImeAction.Next
            ),
            fieldShape = Shapes.medium,
            hintText = hint,
            hintColor = MaterialTheme.colorScheme.outline,
            textStyle = Typography.titleLarge,
            modifier = Modifier.height(50.dp),
            focusedBorderColor = Primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            showIcons = false,
        )
    }
}