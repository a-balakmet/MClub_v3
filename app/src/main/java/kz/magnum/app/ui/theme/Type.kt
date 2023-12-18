package kz.magnum.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import kz.magnum.app.R

val BoldFont = FontFamily(Font(R.font.cera_bold))
val MediumFont = FontFamily(Font(R.font.cera_medium))
val RegularFont = FontFamily(Font(R.font.cera_regular))

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = BoldFont,
        fontSize = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = BoldFont,
        fontSize = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = BoldFont,
        fontSize = 20.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = BoldFont,
        fontSize = 18.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = MediumFont,
        fontSize = 18.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = BoldFont,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = MediumFont,
        fontSize = 16.sp
    ),
    titleMedium = TextStyle(
        fontFamily = RegularFont,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = BoldFont,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = MediumFont,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = RegularFont,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = RegularFont,
        fontSize = 12.sp
    ),
)