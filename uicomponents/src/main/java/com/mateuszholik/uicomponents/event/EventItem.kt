package com.mateuszholik.uicomponents.event

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.cornerRadius
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.extensions.asTimeString
import com.mateuszholik.uicomponents.text.TitleMediumText
import com.mateuszholik.uicomponents.text.TitleSmallText
import java.time.LocalDateTime

@Composable
fun EventItem(
    title: String,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    allDay: Boolean,
    color: Int?,
    onEventClicked: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    Row(
        modifier = modifier
            .clickable { onEventClicked() }
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(MaterialTheme.cornerRadius.normal)
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(start = MaterialTheme.spacing.normal)
                .size(MaterialTheme.sizing.extraTiny)
                .background(
                    color = color?.let { Color(it) } ?: MaterialTheme.colorScheme.secondaryContainer,
                    shape = CircleShape
                )
        )
        if (allDay) {
            TitleMediumText(
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.spacing.normal,
                    vertical = MaterialTheme.spacing.small,
                ),
                text = title, color = contentColor
            )
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                TitleMediumText(
                    modifier = Modifier.padding(
                        start = MaterialTheme.spacing.normal,
                        end = MaterialTheme.spacing.normal,
                        top = MaterialTheme.spacing.small,
                    ),
                    text = title, color = contentColor
                )
                TitleSmallText(
                    modifier = Modifier.padding(
                        start = MaterialTheme.spacing.normal,
                        bottom = MaterialTheme.spacing.small
                    ),
                    text = "${startTime.asTimeString()} - ${endTime.asTimeString()}",
                    color = contentColor
                )

            }
        }
    }
}

@SmallPhonePreview
@Composable
private fun SmallPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SUMMER) {
        EventItem(
            modifier = Modifier.fillMaxWidth(),
            title = "Event 1",
            startTime = LocalDateTime.of(2023, 11, 12, 13, 0),
            endTime = LocalDateTime.of(2023, 11, 12, 14, 0),
            allDay = false,
            color = null,
            onEventClicked = { }
        )
    }
}

@MediumPhonePreview
@Composable
private fun MediumPhonePreview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        EventItem(
            modifier = Modifier.fillMaxWidth(),
            title = "Event 2",
            startTime = LocalDateTime.of(2023, 11, 12, 13, 0),
            endTime = LocalDateTime.of(2023, 11, 12, 14, 0),
            allDay = true,
            color = null,
            onEventClicked = { }
        )
    }
}

@BigPhonePreview
@Composable
private fun BigPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SPRING, darkTheme = true) {
        EventItem(
            modifier = Modifier.fillMaxWidth(),
            title = "Event 3",
            startTime = LocalDateTime.of(2023, 11, 12, 13, 0),
            endTime = LocalDateTime.of(2023, 11, 12, 14, 0),
            allDay = true,
            color = null,
            onEventClicked = { }
        )
    }
}
