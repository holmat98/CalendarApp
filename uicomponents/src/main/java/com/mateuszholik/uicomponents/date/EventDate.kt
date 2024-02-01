package com.mateuszholik.uicomponents.date

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.dateutils.extensions.asDayString
import com.mateuszholik.dateutils.extensions.asFullDayString
import com.mateuszholik.dateutils.extensions.asFullDayTimeString
import com.mateuszholik.dateutils.extensions.asTimeString
import com.mateuszholik.dateutils.extensions.isSameDay
import com.mateuszholik.uicomponents.text.TitleSmallText
import java.time.LocalDateTime

@Composable
fun EventDate(
    startDate: LocalDateTime,
    endDate: LocalDateTime,
    allDay: Boolean,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
) {
    TitleSmallText(
        modifier = modifier,
        text = getTextForDate(startDate, endDate, allDay),
        color = color
    )
}

private fun getTextForDate(
    startDate: LocalDateTime,
    endDate: LocalDateTime,
    allDay: Boolean,
): String =
    when {
        allDay -> startDate.asDayString()
        startDate.isSameDay(endDate) ->
            "${startDate.asFullDayString()} \u25CF ${startDate.asTimeString()} - ${endDate.asTimeString()}"
        else -> "${startDate.asFullDayTimeString()} - ${endDate.asFullDayTimeString()}"
    }

@Preview
@Composable
private fun Preview1() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            EventDate(
                startDate = LocalDateTime.of(2024, 1, 1, 13, 0, 0),
                endDate = LocalDateTime.of(2024, 1, 1, 14, 30, 0),
                allDay = false
            )
        }
    }
}

@Preview
@Composable
private fun Preview2() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            EventDate(
                startDate = LocalDateTime.of(2024, 1, 10, 0, 0, 0),
                endDate = LocalDateTime.of(2024, 1, 10, 0, 0, 0),
                allDay = true
            )
        }
    }
}

@Preview
@Composable
private fun Preview3() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            EventDate(
                startDate = LocalDateTime.of(2024, 1, 1, 18, 0, 0),
                endDate = LocalDateTime.of(2024, 1, 2, 9, 0, 0),
                allDay = false
            )
        }
    }
}
