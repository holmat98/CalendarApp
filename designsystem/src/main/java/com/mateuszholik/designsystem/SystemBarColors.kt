package com.mateuszholik.designsystem

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun ChangeSystemBarColors(
    navigationBarColor: Color = Color.Transparent,
    areIconsDark: Boolean = !isSystemInDarkTheme(),
) {
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = navigationBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = areIconsDark
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = areIconsDark
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }
}
