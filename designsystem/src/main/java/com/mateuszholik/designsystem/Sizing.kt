package com.mateuszholik.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class Sizing(
    val normal: Dp,
    val big: Dp,
) {
    SMALL(
        normal = 30.dp,
        big = 120.dp,
    ),
    MEDIUM(
        normal = 40.dp,
        big = 220.dp,
    ),
    BIG(
        normal = 60.dp,
        big = 320.dp,
    )
}

internal val LocalSizing = staticCompositionLocalOf { Sizing.MEDIUM }

val MaterialTheme.sizing: Sizing
    @Composable
    @ReadOnlyComposable
    get() = LocalSizing.current
