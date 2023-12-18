package kz.magnum.app.ui.singleViews.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kz.magnum.app.ui.theme.AppLightGray

@Composable
fun CircleCloseButton(modifier: Modifier, backIcon: ImageVector = Icons.Default.Close, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.size(48.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0x80E8E8E8), contentColor = AppLightGray)
    ) {
        Icon(imageVector = backIcon, contentDescription = "close", tint = Color.White)
    }
}