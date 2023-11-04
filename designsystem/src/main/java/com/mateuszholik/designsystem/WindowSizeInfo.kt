package com.mateuszholik.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

@Composable
internal fun rememberWindowSizeInfo(): WindowSizeInfo {
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

internal data class WindowSizeInfo(
    val widthInfo: WindowType,
    val heightInfo: WindowType,
)

internal enum class WindowType {
    SMALL,
    MEDIUM,
    BIG
}
