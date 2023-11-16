package com.mateuszholik.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class Sizing(
    val extraTiny: Dp,
    val normal: Dp,
    val big: Dp,
) {
    SMALL(
        extraTiny = 5.dp,
        normal = 30.dp,
        big = 120.dp,
    ),
    MEDIUM(
        extraTiny = 7.dp,
        normal = 40.dp,
        big = 220.dp,
    ),
    BIG(
        extraTiny = 10.dp,
        normal = 60.dp,
        big = 320.dp,
    )
}

internal val LocalSizing = staticCompositionLocalOf { Sizing.MEDIUM }

val MaterialTheme.sizing: Sizing
    @Composable
    @ReadOnlyComposable
    get() = LocalSizing.current
