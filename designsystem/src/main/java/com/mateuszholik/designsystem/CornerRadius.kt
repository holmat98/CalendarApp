package com.mateuszholik.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class CornerRadius(
    val small: Dp,
    val normal: Dp,
) {
    SMALL(
        small = 8.dp,
        normal = 16.dp
    ),
    MEDIUM(
        small = 12.dp,
        normal = 24.dp
    ),
    BIG(
        small = 20.dp,
        normal = 40.dp
    )
}

internal val LocalCornerRadius = staticCompositionLocalOf { CornerRadius.MEDIUM }

val MaterialTheme.cornerRadius: CornerRadius
    @Composable
    @ReadOnlyComposable
    get() = LocalCornerRadius.current
