package com.mateuszholik.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val _playFair = FontFamily(
    Font(resId = R.font.playfair_display_regular, weight = FontWeight.Normal),
    Font(
        resId = R.font.playfair_display_italic,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),
    Font(resId = R.font.playfair_display_bold, weight = FontWeight.Bold),
    Font(
        resId = R.font.playfair_display_bold_italic,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    ),
    Font(resId = R.font.playfair_display_extra_bold, weight = FontWeight.ExtraBold),
    Font(
        resId = R.font.playfair_display_extra_bold_italic,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Italic
    ),
    Font(resId = R.font.playfair_display_medium, weight = FontWeight.Medium),
    Font(
        resId = R.font.playfair_display_medium_italic,
        weight = FontWeight.Medium,
        style = FontStyle.Italic
    ),
    Font(resId = R.font.playfair_display_semi_bold, weight = FontWeight.SemiBold),
    Font(
        resId = R.font.playfair_display_semi_bold_italic,
        weight = FontWeight.SemiBold,
        style = FontStyle.Italic
    ),
    Font(resId = R.font.playfair_display_black, weight = FontWeight.Black),
    Font(
        resId = R.font.playfair_display_black_italic,
        weight = FontWeight.Black,
        style = FontStyle.Italic
    ),
)

internal val SmallTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.2).sp
    ),
    displayMedium = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.2.sp
    ),
    titleSmall = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.2.sp
    ),
    bodySmall = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
)

internal val MediumTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 67.sp,
        lineHeight = 74.sp,
        letterSpacing = (-0.2).sp
    ),
    displayMedium = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 55.sp,
        lineHeight = 62.sp,
        letterSpacing = 0.0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 46.sp,
        lineHeight = 54.sp,
        letterSpacing = 0.0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 37.sp,
        lineHeight = 45.sp,
        letterSpacing = 0.0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 33.sp,
        lineHeight = 41.sp,
        letterSpacing = 0.0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 29.sp,
        lineHeight = 37.sp,
        letterSpacing = 0.0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 27.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 21.sp,
        lineHeight = 29.sp,
        letterSpacing = 0.2.sp
    ),
    titleSmall = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 19.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 21.sp,
        lineHeight = 29.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 19.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.2.sp
    ),
    bodySmall = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 19.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.5.sp
    ),
)

internal val BigTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 87.sp,
        lineHeight = 94.sp,
        letterSpacing = (-0.2).sp
    ),
    displayMedium = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 75.sp,
        lineHeight = 82.sp,
        letterSpacing = 0.0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 66.sp,
        lineHeight = 74.sp,
        letterSpacing = 0.0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 47.sp,
        lineHeight = 55.sp,
        letterSpacing = 0.0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 43.sp,
        lineHeight = 51.sp,
        letterSpacing = 0.0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 39.sp,
        lineHeight = 47.sp,
        letterSpacing = 0.0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 37.sp,
        lineHeight = 43.sp,
        letterSpacing = 0.0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 31.sp,
        lineHeight = 39.sp,
        letterSpacing = 0.2.sp
    ),
    titleSmall = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 29.sp,
        lineHeight = 35.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 31.sp,
        lineHeight = 39.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 29.sp,
        lineHeight = 35.sp,
        letterSpacing = 0.2.sp
    ),
    bodySmall = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 27.sp,
        lineHeight = 31.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 29.sp,
        lineHeight = 35.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 27.sp,
        lineHeight = 31.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = _playFair,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 31.sp,
        letterSpacing = 0.5.sp
    ),
)
