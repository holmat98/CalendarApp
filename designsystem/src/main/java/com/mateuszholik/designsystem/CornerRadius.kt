package com.mateuszholik.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class CornerRadius(
    val normal: Dp,
) {
    SMALL(normal = 16.dp),
    MEDIUM(normal = 24.dp),
    BIG(normal = 40.dp)
}

internal val LocalCornerRadius = staticCompositionLocalOf { CornerRadius.MEDIUM }

val MaterialTheme.cornerRadius: CornerRadius
    @Composable
    @ReadOnlyComposable
    get() = LocalCornerRadius.current
