package com.mateuszholik.uicomponents.attendee

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.text.TitleMediumText

@Composable
fun AttendeeItem(
    name: String,
    status: Status,
    modifier: Modifier = Modifier,
    colors: AttendeeItemColors = AttendeeItemDefaults.colors(),
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(MaterialTheme.sizing.normal)
                .background(
                    color = colors.iconContainerColor,
                    shape = CircleShape,
                )
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Outlined.Face,
                contentDescription = null,
                tint = colors.iconContentColor
            )

            Box(
                modifier = Modifier
                    .size(MaterialTheme.sizing.tiny)
                    .background(
                        color = colors.iconContainerColor,
                        shape = CircleShape,
                    )
                    .align(alignment = Alignment.BottomEnd)
            ) {
                Icon(
                    modifier = Modifier
                        .padding(1.dp)
                        .fillMaxSize(),
                    imageVector = status.toIcon(),
                    contentDescription = null,
                    tint = status.toColor()
                )
            }
        }

        TitleMediumText(
            modifier = Modifier.padding(start = MaterialTheme.spacing.normal),
            text = name,
            color = colors.textColor
        )
    }
}

data class AttendeeItemColors internal constructor(
    val iconContainerColor: Color,
    val iconContentColor: Color,
    val textColor: Color,
)

object AttendeeItemDefaults {

    @Composable
    @ReadOnlyComposable
    fun colors(
        iconContainerColor: Color = LocalContentColor.current,
        iconContentColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        textColor: Color = LocalContentColor.current,
    ): AttendeeItemColors =
        AttendeeItemColors(
            iconContainerColor = iconContainerColor,
            iconContentColor = iconContentColor,
            textColor = textColor
        )
}

private fun Status.toIcon(): ImageVector =
    when (this) {
        Status.TENTATIVE,
        Status.INVITED,
        Status.NONE -> Icons.Filled.Info
        Status.ACCEPTED -> Icons.Filled.CheckCircle
        Status.DECLINED -> Icons.Filled.Close
    }

@Composable
@ReadOnlyComposable
private fun Status.toColor(): Color =
    when (this) {
        Status.TENTATIVE,
        Status.NONE,
        -> MaterialTheme.colorScheme.outline
        Status.ACCEPTED -> Color(0xFF3EF7B1)
        Status.DECLINED -> MaterialTheme.colorScheme.error
        Status.INVITED -> Color(0xFFE2D339)
    }

enum class Status {
    NONE,
    ACCEPTED,
    DECLINED,
    INVITED,
    TENTATIVE,
}

@SmallPhonePreview
@Composable
fun SmallPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SUMMER) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Column(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal)) {
                AttendeeItem(name = "Jan Kowalski", status = Status.INVITED)
                AttendeeItem(
                    modifier = Modifier.padding(vertical = MaterialTheme.spacing.normal),
                    name = "Piotr Nowak",
                    status = Status.ACCEPTED
                )
                AttendeeItem(
                    name = "Piotr Nowak",
                    status = Status.TENTATIVE
                )
                AttendeeItem(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.normal),
                    name = "Mateusz Holik",
                    status = Status.DECLINED
                )
            }
        }
    }
}

@MediumPhonePreview
@Composable
fun MediumPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Column(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal)) {
                AttendeeItem(name = "Jan Kowalski", status = Status.INVITED)
                AttendeeItem(
                    modifier = Modifier.padding(vertical = MaterialTheme.spacing.normal),
                    name = "Piotr Nowak",
                    status = Status.ACCEPTED
                )
                AttendeeItem(
                    name = "Piotr Nowak",
                    status = Status.TENTATIVE
                )
                AttendeeItem(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.normal),
                    name = "Mateusz Holik", status = Status.DECLINED)
            }
        }
    }
}

@MediumPhonePreview
@Composable
fun MediumPhonePreviewDarkTheme() {
    CalendarAppTheme(styleType = StyleType.AUTUMN, darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Column(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal)) {
                AttendeeItem(name = "Jan Kowalski", status = Status.INVITED)
                AttendeeItem(
                    modifier = Modifier.padding(vertical = MaterialTheme.spacing.normal),
                    name = "Piotr Nowak",
                    status = Status.ACCEPTED
                )
                AttendeeItem(
                    name = "Piotr Nowak",
                    status = Status.TENTATIVE
                )
                AttendeeItem(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.normal),
                    name = "Mateusz Holik",
                    status = Status.DECLINED
                )
            }
        }
    }
}

@BigPhonePreview
@Composable
fun BigPhonePreview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Column(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal)) {
                AttendeeItem(name = "Jan Kowalski", status = Status.INVITED)
                AttendeeItem(
                    modifier = Modifier.padding(vertical = MaterialTheme.spacing.normal),
                    name = "Piotr Nowak",
                    status = Status.ACCEPTED
                )
                AttendeeItem(
                    name = "Piotr Nowak",
                    status = Status.TENTATIVE
                )
                AttendeeItem(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.normal),
                    name = "Mateusz Holik",
                    status = Status.DECLINED
                )
            }
        }
    }
}
