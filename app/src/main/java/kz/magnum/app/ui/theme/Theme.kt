package kz.magnum.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = Color.White,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
    background = AppBlack,
    onBackground = Color.White,
    surface = Color(0xFF262629),
    onSurface = Color.White,
    surfaceTint = Color(0xFF3A3A3C),
    tertiaryContainer = Color(0xFF3A3A3C),
    onTertiaryContainer = Color(0xFFBDBEC2),
    surfaceVariant = Color.Black,
    onSurfaceVariant = Color.White,
    outline = Color(0xFF3A3A3C), // aka DividerColor
)

private val lightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
    background = Color(0xFFFBFBFB),
    onBackground = AppTextColor,
    surface = Color.White,
    onSurface = AppTextColor,
    surfaceTint = Color(0xFFEAEAEA),
    tertiaryContainer = Color(0xFFF3F3F3),
    onTertiaryContainer = Color(0xFFBDBEC2),
    surfaceVariant = Color.White,
    onSurfaceVariant = Color.Black,
    outline = Color(0xFFBDBEC2), // aka DividerColor
)

@Composable
fun MagnumClubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    //dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = if (darkTheme) Color.Black.toArgb() else Color.White.toArgb()
            //window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}