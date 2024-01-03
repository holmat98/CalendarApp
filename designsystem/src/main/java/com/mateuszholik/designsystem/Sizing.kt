package com.mateuszholik.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class Sizing(
    val extraTiny: Dp,
    val tiny: Dp,
    val small: Dp,
    val normal: Dp,
    val big: Dp,
    val extraBig: Dp,
) {
    SMALL(
        extraTiny = 5.dp,
        tiny = 15.dp,
        small = 20.dp,
        normal = 30.dp,
        big = 60.dp,
        extraBig = 120.dp,
    ),
    MEDIUM(
        extraTiny = 7.dp,
        tiny = 18.dp,
        small = 26.dp,
        normal = 40.dp,
        big = 80.dp,
        extraBig = 160.dp,
    ),
    BIG(
        extraTiny = 10.dp,
        tiny = 30.dp,
        small = 40.dp,
        normal = 60.dp,
        big = 120.dp,
        extraBig = 240.dp,
    )
}

internal val LocalSizing = staticCompositionLocalOf { Sizing.MEDIUM }

val MaterialTheme.sizing: Sizing
    @Composable
    @ReadOnlyComposable
    get() = LocalSizing.current
