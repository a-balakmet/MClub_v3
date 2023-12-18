package kz.magnum.app.ui.singleViews

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kz.magnum.app.ui.theme.Primary
import kz.magnum.app.ui.theme.Typography

@Composable
fun NumPad(
    modifier: Modifier,
    onStart: Boolean = false,
    onClick: (String) -> Unit
) {

    @Composable
    fun NumPadButton(char: String) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val buttonColor = if (isPressed)
            if (onStart) Color.White else Primary
        else
            if (onStart) Color.Unspecified else MaterialTheme.colorScheme.surface
        val textColor = if (isPressed)
            if (onStart) Primary else Color.White
        else
            if (onStart) Color.White else MaterialTheme.colorScheme.onBackground
        Button(
            onClick = { onClick(char) },
            interactionSource = interactionSource,
            modifier = Modifier
                .size(75.dp)
                .border(
                    width = 1.dp,
                    shape = CircleShape,
                    color = if (isPressed) Color.Unspecified else if (onStart) Color.White else MaterialTheme.colorScheme.onBackground
                ),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = textColor)
        ) {
            Text(text = char, style = Typography.displayLarge, color = textColor)
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("1", "2", "3").forEach {
                NumPadButton(char = it)
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("4", "5", "6").forEach {
                NumPadButton(char = it)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("7", "8", "9").forEach {
                NumPadButton(char = it)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("-", "0", "del").forEach {
                when (it) {
                    "-" -> Box(modifier = Modifier.size(75.dp))
                    "del" -> Box(modifier = Modifier.size(75.dp)) {
                        IconButton(
                            onClick = { onClick("del") },
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Backspace,
                                contentDescription = "delete",
                                modifier = Modifier.size(32.dp),
                                tint = if (onStart) Color.White else MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    else -> {
                        NumPadButton(it)
                    }
                }
            }
        }
    }
}