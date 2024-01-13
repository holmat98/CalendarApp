package com.mateuszholik.uicomponents.event

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.date.EventDate
import com.mateuszholik.uicomponents.text.TitleMediumText
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
    colors: EventColors = EventDefaults.colors(),
) {
    Card(
        modifier = modifier.clickable { onEventClicked() },
        elevation = CardDefaults.elevatedCardElevation(),
        colors = CardDefaults.cardColors(
            containerColor = colors.containerColor,
            contentColor = colors.contentColor,
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.spacing.tiny,
                        start = MaterialTheme.spacing.normal
                    )
                    .size(MaterialTheme.sizing.extraTiny)
                    .background(
                        color = color?.let { Color(it) }
                            ?: colors.defaultEventColor,
                        shape = CircleShape
                    )
            )
            TitleMediumText(
                modifier = Modifier.padding(
                    start = MaterialTheme.spacing.normal,
                    end = MaterialTheme.spacing.normal,
                ),
                text = title,
            )
        }
        EventDate(
            modifier = Modifier.padding(
                start = MaterialTheme.spacing.normal,
                bottom = MaterialTheme.spacing.small,
            ),
            startDate = startTime,
            endDate = endTime,
            allDay = allDay
        )
    }
}

@Immutable
data class EventColors internal constructor(
    val containerColor: Color,
    val contentColor: Color,
    val defaultEventColor: Color,
)

object EventDefaults {

    @Composable
    @ReadOnlyComposable
    fun colors(
        containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        defaultEventColor: Color = MaterialTheme.colorScheme.inversePrimary,
    ): EventColors =
        EventColors(
            containerColor = containerColor,
            contentColor = contentColor,
            defaultEventColor = defaultEventColor
        )
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
