package com.mateuszholik.calendarapp.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
@ReadOnlyComposable
fun rememberWindowSize(): WindowSizeInfo {
    val configuration = LocalConfiguration.current

    return WindowSizeInfo(
        widthInfo = when (configuration.screenWidthDp) {
            in 1..400 -> WindowType.SMALL
            in 401..600 -> WindowType.MEDIUM
            else -> WindowType.BIG
        },
        heightInfo = when (configuration.screenHeightDp) {
            in 1..400 -> WindowType.SMALL
            in 401..600 -> WindowType.MEDIUM
            else -> WindowType.BIG
        }
    )
}

data class WindowSizeInfo(
    val widthInfo: WindowType,
    val heightInfo: WindowType,
)

enum class WindowType {
    SMALL,
    MEDIUM,
    BIG
}
