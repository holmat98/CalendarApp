package com.mateuszholik.designsystem

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.mateuszholik.designsystem.autumn.AutumnDarkColors
import com.mateuszholik.designsystem.autumn.AutumnLightColors
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.spring.SpringDarkColors
import com.mateuszholik.designsystem.spring.SpringLightColors
import com.mateuszholik.designsystem.summer.SummerDarkColors
import com.mateuszholik.designsystem.summer.SummerLightColors
import com.mateuszholik.designsystem.winter.WinterDarkColors
import com.mateuszholik.designsystem.winter.WinterLightColors

@Composable
fun CalendarAppTheme(
    styleType: StyleType,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) {
        styleType.darkMode()
    } else {
        styleType.lightMode()
    }

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    val windowSizeInfo = rememberWindowSizeInfo()

    CompositionLocalProvider(
        LocalSizing provides windowSizeInfo.widthInfo.toSizing(),
        LocalSpacing provides windowSizeInfo.widthInfo.toSpacing(),
        LocalCornerRadius provides windowSizeInfo.widthInfo.toCornerRadius(),
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = windowSizeInfo.widthInfo.toTypography(),
            content = content
        )
    }
}

private fun StyleType.lightMode(): ColorScheme =
    when (this) {
        StyleType.SPRING -> SpringLightColors
        StyleType.SUMMER -> SummerLightColors
        StyleType.AUTUMN -> AutumnLightColors
        StyleType.WINTER -> WinterLightColors
    }

private fun StyleType.darkMode(): ColorScheme =
    when (this) {
        StyleType.SPRING -> SpringDarkColors
        StyleType.SUMMER -> SummerDarkColors
        StyleType.AUTUMN -> AutumnDarkColors
        StyleType.WINTER -> WinterDarkColors
    }

private fun WindowType.toTypography(): Typography =
    when (this) {
        WindowType.SMALL -> SmallTypography
        WindowType.MEDIUM -> MediumTypography
        WindowType.BIG -> BigTypography
    }

private fun WindowType.toSizing(): Sizing =
    when (this) {
        WindowType.SMALL -> Sizing.SMALL
        WindowType.MEDIUM -> Sizing.MEDIUM
        WindowType.BIG -> Sizing.BIG
    }

private fun WindowType.toSpacing(): Spacing =
    when (this) {
        WindowType.SMALL -> Spacing.SMALL
        WindowType.MEDIUM -> Spacing.MEDIUM
        WindowType.BIG -> Spacing.BIG
    }

private fun WindowType.toCornerRadius(): CornerRadius =
    when (this) {
        WindowType.SMALL -> CornerRadius.SMALL
        WindowType.MEDIUM -> CornerRadius.MEDIUM
        WindowType.BIG -> CornerRadius.BIG
    }
