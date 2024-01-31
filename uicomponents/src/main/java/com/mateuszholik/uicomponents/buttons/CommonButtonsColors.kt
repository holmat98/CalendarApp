package com.mateuszholik.uicomponents.buttons

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

@Immutable
data class CommonButtonColors internal constructor(
    val containerColor: Color,
    val contentColor: Color,
)

object CommonButtonDefaults {

    @Composable
    @ReadOnlyComposable
    fun commonButtonColors(
        containerColor: Color = MaterialTheme.colorScheme.primary,
        contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    ): CommonButtonColors =
        CommonButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        )

    @Composable
    @ReadOnlyComposable
    fun commonOutlinedButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = MaterialTheme.colorScheme.primary,
    ): CommonButtonColors =
        CommonButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        )

    @Composable
    @ReadOnlyComposable
    fun commonTextButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = MaterialTheme.colorScheme.secondary,
    ): CommonButtonColors =
        CommonButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        )
}
