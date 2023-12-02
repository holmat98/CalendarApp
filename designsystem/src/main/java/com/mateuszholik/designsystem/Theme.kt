package com.mateuszholik.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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

    ChangeSystemBarColors(
        statusBarColor = colorScheme.surface,
        navigationBarColor = colorScheme.surface,
    )
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
