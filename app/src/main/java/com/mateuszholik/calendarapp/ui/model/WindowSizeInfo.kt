package com.mateuszholik.calendarapp.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowSize(): WindowSizeInfo {
    val configuration = LocalConfiguration.current

    return remember {
        WindowSizeInfo(
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
