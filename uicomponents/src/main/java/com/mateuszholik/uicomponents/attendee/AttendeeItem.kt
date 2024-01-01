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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Info
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
    textColor: Color = LocalContentColor.current,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(MaterialTheme.sizing.small)
                .background(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = CircleShape,
                )
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Outlined.Face,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onTertiaryContainer
            )

            Box(
                modifier = Modifier
                    .size(MaterialTheme.sizing.tiny)
                    .background(
                        color = status.toContainerColor(),
                        shape = CircleShape,
                    )
                    .align(alignment = Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = status.toIcon(),
                    contentDescription = null,
                    tint = status.toContentColor()
                )
            }
        }

        TitleMediumText(
            modifier = Modifier.padding(start = MaterialTheme.spacing.normal),
            text = name,
            color = textColor
        )
    }
}

private fun Status.toIcon(): ImageVector =
    when (this) {
        Status.TENTATIVE,
        Status.INVITED,
        Status.NONE -> Icons.Outlined.Info
        Status.ACCEPTED -> Icons.Default.Done
        Status.DECLINED -> Icons.Default.Close
    }

@Composable
@ReadOnlyComposable
private fun Status.toContainerColor(): Color =
    when (this) {
        Status.TENTATIVE,
        Status.NONE -> MaterialTheme.colorScheme.surfaceVariant
        Status.ACCEPTED -> Color(0xFF3EF7B1)
        Status.DECLINED -> MaterialTheme.colorScheme.error
        Status.INVITED -> Color(0xFFF7E63E)
    }

@Composable
@ReadOnlyComposable
private fun Status.toContentColor(): Color =
    when (this) {
        Status.TENTATIVE,
        Status.NONE -> MaterialTheme.colorScheme.onSurfaceVariant
        Status.ACCEPTED -> Color(0xFF28A072)
        Status.DECLINED -> MaterialTheme.colorScheme.onError
        Status.INVITED -> Color(0xFFA09428)
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
                AttendeeItem(name = "Mateusz Holik", status = Status.DECLINED)
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
                AttendeeItem(name = "Mateusz Holik", status = Status.DECLINED)
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
                AttendeeItem(name = "Mateusz Holik", status = Status.DECLINED)
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
                AttendeeItem(name = "Mateusz Holik", status = Status.DECLINED)
            }
        }
    }
}
