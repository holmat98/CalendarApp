package com.mateuszholik.uicomponents.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType

@Composable
fun CommonIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: CommonIconButtonColors = CommonIconButtonDefaults.colors(),
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = colors.containerColor,
            contentColor = colors.contentColor
        )
    ) {
        Icon(imageVector = imageVector, contentDescription = null)
    }
}

@Immutable
data class CommonIconButtonColors internal constructor(
    val containerColor: Color,
    val contentColor: Color,
)

object CommonIconButtonDefaults {

    @Composable
    @ReadOnlyComposable
    fun colors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = LocalContentColor.current,
    ): CommonIconButtonColors =
        CommonIconButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        )
}

@Preview
@Composable
private fun Preview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            CommonIconButton(imageVector = Icons.Filled.AddCircle, onClick = { })
        }
    }
}
