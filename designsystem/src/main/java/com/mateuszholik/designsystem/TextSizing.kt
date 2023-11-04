package com.mateuszholik.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

enum class TextSizing(val bigHeader: TextUnit) {
    SMALL(bigHeader = 50.sp),
    MEDIUM(bigHeader = 65.sp),
    BIG(bigHeader = 85.sp)
}

val LocalTextSizing = staticCompositionLocalOf { TextSizing.MEDIUM }

val MaterialTheme.textSizing: TextSizing
    @Composable
    @ReadOnlyComposable
    get() = LocalTextSizing.current
