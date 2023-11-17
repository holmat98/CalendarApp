package com.mateuszholik.uicomponents.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.extensions.conditional
import com.mateuszholik.uicomponents.models.CalendarField
import com.mateuszholik.uicomponents.text.BodyLargeText
import com.mateuszholik.uicomponents.text.DateText
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarView(
    currentDay: LocalDate,
    currentMonth: YearMonth,
    daysWithEvents: List<LocalDate>,
    onDateChanged: (newDate: LocalDate) -> Unit,
    onMonthChanged: (newMonth: YearMonth) -> Unit,
    modifier: Modifier = Modifier,
    colors: CalendarViewDefaults.Colors = CalendarViewDefaults.defaultColors(),
) {
    val days = CalendarField.createFieldsForMonth(currentMonth).chunked(7)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.spacing.normal),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DateText(
                modifier = Modifier.weight(1f),
                date = currentMonth,
                textColor = colors.foregroundColor
            )

            IconButton(
                modifier = Modifier.size(MaterialTheme.sizing.normal),
                onClick = { onMonthChanged(currentMonth.minusMonths(1)) }
            ) {
                Icon(
                    modifier = Modifier.size(MaterialTheme.sizing.normal),
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = null,

                )
            }

            IconButton(
                onClick = { onMonthChanged(currentMonth.plusMonths(1)) }
            ) {
                Icon(
                    modifier = Modifier.size(MaterialTheme.sizing.normal),
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DayOfWeek.values().forEach {
                CalendarText(
                    text = it.getDisplayName(TextStyle.NARROW_STANDALONE, Locale.getDefault()),
                    hasEvent = false,
                    textColor = colors.foregroundColor
                )
            }
        }

        Divider(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.small),
            color = MaterialTheme.colorScheme.secondaryContainer
        )

        days.forEach { week ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.small),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                week.forEach {
                    when (it) {
                        is CalendarField.Empty -> Spacer(modifier = Modifier.size(MaterialTheme.sizing.normal))
                        is CalendarField.Day -> if (it.date == currentDay) {
                            CurrentDay(
                                text = "${it.date.dayOfMonth}",
                                hasEvent = it.date in daysWithEvents,
                                backgroundColor = colors.selectedDayColor,
                                textColor = colors.selectedDayTextColor
                            )
                        } else {
                            CalendarText(
                                text = "${it.date.dayOfMonth}",
                                hasEvent = it.date in daysWithEvents,
                                textColor = colors.foregroundColor,
                                onClick = { onDateChanged(it.date) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrentDay(
    text: String,
    hasEvent: Boolean,
    backgroundColor: Color,
    textColor: Color,
) {
    Column(
        modifier = Modifier
            .size(MaterialTheme.sizing.normal)
            .clip(CircleShape)
            .background(backgroundColor)
            .conditional(
                condition = hasEvent
            ) {
                border(
                    width = 1.dp,
                    color = textColor,
                    shape = CircleShape,
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BodyLargeText(
            text = text,
            color = textColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun CalendarText(
    text: String,
    hasEvent: Boolean,
    textColor: Color,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .size(MaterialTheme.sizing.normal)
            .clip(CircleShape)
            .clickable { onClick() }
            .conditional(
                condition = hasEvent
            ) {
                border(
                    width = 1.dp,
                    color = textColor,
                    shape = CircleShape,
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BodyLargeText(
            text = text,
            color = textColor,
        )
    }
}

object CalendarViewDefaults {

    @Composable
    @ReadOnlyComposable
    fun defaultColors(): Colors =
        Colors(
            backgroundColor = MaterialTheme.colorScheme.secondary,
            foregroundColor = MaterialTheme.colorScheme.onSecondary,
            selectedDayColor = MaterialTheme.colorScheme.secondaryContainer,
            selectedDayTextColor = MaterialTheme.colorScheme.onSecondaryContainer
        )

    data class Colors(
        val backgroundColor: Color,
        val foregroundColor: Color,
        val selectedDayColor: Color,
        val selectedDayTextColor: Color,
    )
}

@SmallPhonePreview
@Composable
private fun Preview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        Surface(color = MaterialTheme.colorScheme.secondary) {
            CalendarView(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal),
                currentDay = LocalDate.of(2023, 11, 5),
                currentMonth = YearMonth.of(2023, 11),
                daysWithEvents = listOf(
                    LocalDate.of(2023, 11, 1),
                    LocalDate.of(2023, 11, 5),
                    LocalDate.of(2023, 11, 16),
                    LocalDate.of(2023, 11, 24),
                ),
                onDateChanged = {},
                onMonthChanged = {},
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
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal),
                currentDay = LocalDate.of(2023, 12, 6),
                currentMonth = YearMonth.of(2023, 12),
                daysWithEvents = listOf(
                    LocalDate.of(2023, 12, 8),
                    LocalDate.of(2023, 12, 15),
                    LocalDate.of(2023, 12, 16),
                    LocalDate.of(2023, 12, 31),
                ),
                onDateChanged = {},
                onMonthChanged = {},
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
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal),
                currentDay = LocalDate.of(2024, 1, 15),
                currentMonth = YearMonth.of(2024, 1),
                daysWithEvents = listOf(
                    LocalDate.of(2024, 1, 8),
                    LocalDate.of(2024, 1, 15),
                    LocalDate.of(2024, 1, 16),
                    LocalDate.of(2024, 1, 31),
                ),
                onDateChanged = {},
                onMonthChanged = {},
            )
        }
    }
}
