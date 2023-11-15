package com.mateuszholik.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class Spacing(
    val small: Dp,
    val normal: Dp,
    val big: Dp,
    val extraBig: Dp,
) {
    SMALL(
        small = 8.dp,
        normal = 16.dp,
        big = 24.dp,
        extraBig = 48.dp,
    ),
    MEDIUM(
        small = 12.dp,
        normal = 24.dp,
        big = 36.dp,
        extraBig = 72.dp,
    ),
    BIG(
        small = 20.dp,
        normal = 40.dp,
        big = 60.dp,
        extraBig = 120.dp,
    )
}

internal val LocalSpacing = staticCompositionLocalOf { Spacing.MEDIUM }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
