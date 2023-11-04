package com.mateuszholik.calendarapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.mateuszholik.calendarapp.ui.model.WindowType
import com.mateuszholik.calendarapp.ui.model.rememberWindowSize
import com.mateuszholik.calendarapp.ui.theme.autumn.AutumnDarkColors
import com.mateuszholik.calendarapp.ui.theme.autumn.AutumnLightColors
import com.mateuszholik.calendarapp.ui.theme.models.StyleType
import com.mateuszholik.calendarapp.ui.theme.spring.SpringDarkColors
import com.mateuszholik.calendarapp.ui.theme.spring.SpringLightColors
import com.mateuszholik.calendarapp.ui.theme.summer.SummerDarkColors
import com.mateuszholik.calendarapp.ui.theme.summer.SummerLightColors
import com.mateuszholik.calendarapp.ui.theme.winter.WinterDarkColors
import com.mateuszholik.calendarapp.ui.theme.winter.WinterLightColors

@Composable
fun CalendarAppTheme(
    styleType: StyleType,
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> styleType.darkMode()
        else -> styleType.lightMode()
    }

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val textSizing = when (rememberWindowSize().widthInfo) {
        WindowType.SMALL -> TextSizing.SMALL
        WindowType.MEDIUM -> TextSizing.MEDIUM
        WindowType.BIG -> TextSizing.BIG
    }

    CompositionLocalProvider(
        LocalTextSizing provides textSizing
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
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
