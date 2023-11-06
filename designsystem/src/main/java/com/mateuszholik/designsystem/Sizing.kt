package com.mateuszholik.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class Sizing(
    val normal: Dp,
) {
    SMALL(normal = 30.dp),
    MEDIUM(normal = 40.dp),
    BIG(normal = 60.dp)
}

val LocalSizing = staticCompositionLocalOf { Sizing.MEDIUM }

val MaterialTheme.sizing: Sizing
    @Composable
    @ReadOnlyComposable
    get() = LocalSizing.current
