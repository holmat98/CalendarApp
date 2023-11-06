package com.mateuszholik.uicomponents.calendar

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.textSizing
import com.mateuszholik.uicomponents.text.DateText
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale
import java.util.stream.Stream

@Composable
fun CalendarView(
    currentDay: LocalDate,
    onDateChanged: (newDate: LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedDate by remember { mutableStateOf(currentDay) }
    val yearMonth = YearMonth.of(selectedDate.year, selectedDate.month)
    val days by remember { mutableStateOf(createDaysForMonth(yearMonth).chunked(7)) }

    Column(modifier = modifier.fillMaxWidth()) {
        DateText(
            modifier = Modifier.padding(bottom = 32.dp),
            date = selectedDate,
            textSize = MaterialTheme.textSizing.header
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DayOfWeek.values().forEach {
                CalendarText(
                    text = it.getDisplayName(TextStyle.NARROW_STANDALONE, Locale.getDefault())
                )
            }
        }

        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.secondaryContainer
        )

        days.forEach { week ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                week.forEach {
                    when (it) {
                        is Day.EmptyDay -> Spacer(modifier = Modifier.size(MaterialTheme.sizing.normal))
                        is Day.CurrentMonthDay -> if (it.date == currentDay) {
                            CurrentDay("${it.date.dayOfMonth}")
                        } else {
                            CalendarText(
                                text = "${it.date.dayOfMonth}",
                                onClick = {
                                    selectedDate = it.date
                                    onDateChanged(it.date)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrentDay(text: String) {
    Box(
        modifier = Modifier
            .size(MaterialTheme.sizing.normal)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = MaterialTheme.textSizing.normal,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun CalendarText(
    text: String,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .size(MaterialTheme.sizing.normal)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = MaterialTheme.textSizing.normal,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

private fun createDaysForMonth(yearMonth: YearMonth): List<Day> {
    val result = mutableListOf<Day>()

    val firstDayOfMonth = yearMonth.atDay(1)
    val nextMonthFirstDay = yearMonth.plusMonths(1).atDay(1)
    val lastDayOfMonth = nextMonthFirstDay.minusDays(1)

    if (firstDayOfMonth.dayOfWeek != DayOfWeek.MONDAY) {
        (1 until firstDayOfMonth.dayOfWeek.value).forEach { _ ->
            result.add(Day.EmptyDay)
        }
    }

    firstDayOfMonth.daysUntil(nextMonthFirstDay).forEach {
        result.add(Day.CurrentMonthDay(it))
    }

    if (lastDayOfMonth.dayOfWeek != DayOfWeek.SUNDAY) {
        ((lastDayOfMonth.dayOfWeek.value + 1)..7).forEach { _ ->
            result.add(Day.EmptyDay)
        }
    }

    return result
}

private fun LocalDate.daysUntil(endDate: LocalDate): Stream<LocalDate> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        datesUntil(endDate)
    } else {
        Stream.iterate(this) {
            it.plusDays(1)
        }.limit(ChronoUnit.DAYS.between(this, endDate) + 1)
    }

private sealed class Day {

    data class CurrentMonthDay(val date: LocalDate) : Day()

    data object EmptyDay : Day()
}

@SmallPhonePreview
@Composable
private fun Preview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        Surface(color = MaterialTheme.colorScheme.secondary) {
            CalendarView(
                modifier = Modifier.padding(horizontal = 16.dp),
                currentDay = LocalDate.of(2023, 11, 5),
                onDateChanged = {}
            )
        }
    }
}

@MediumPhonePreview
@Composable
private fun Preview2() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        Surface(color = MaterialTheme.colorScheme.secondary) {
            CalendarView(
                modifier = Modifier.padding(horizontal = 16.dp),
                currentDay = LocalDate.of(2023, 12, 6),
                onDateChanged = {}
            )
        }
    }
}

@BigPhonePreview
@Composable
private fun Preview3() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(color = MaterialTheme.colorScheme.secondary) {
            CalendarView(
                modifier = Modifier.padding(horizontal = 16.dp),
                currentDay = LocalDate.of(2024, 1, 15),
                onDateChanged = {}
            )
        }
    }
}
