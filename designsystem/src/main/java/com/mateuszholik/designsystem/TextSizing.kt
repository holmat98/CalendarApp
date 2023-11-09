package com.mateuszholik.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

enum class TextSizing(
    val bigHeader: TextUnit,
    val header: TextUnit,
    val normal: TextUnit,
) {
    SMALL(
        bigHeader = 50.sp,
        header = 25.sp,
        normal = 16.sp,
    ),
    MEDIUM(
        bigHeader = 65.sp,
        header = 30.sp,
        normal = 20.sp,
    ),
    BIG(
        bigHeader = 85.sp,
        header = 40.sp,
        normal = 28.sp,
    )
}

val LocalTextSizing = staticCompositionLocalOf { TextSizing.MEDIUM }

val MaterialTheme.textSizing: TextSizing
    @Composable
    @ReadOnlyComposable
    get() = LocalTextSizing.current
