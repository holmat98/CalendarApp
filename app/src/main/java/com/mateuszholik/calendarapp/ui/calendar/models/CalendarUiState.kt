package com.mateuszholik.calendarapp.ui.calendar.models

import androidx.compose.runtime.Stable
import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.domain.models.Event
import java.time.LocalDate
import java.time.YearMonth

@Stable
sealed class CalendarUiState : UiState {

    data class Loading(
        val currentDate: LocalDate,
        val currentMonth: YearMonth,
    ) : CalendarUiState()

    data class CalendarInfo(
        val currentDate: LocalDate,
        val currentMonth: YearMonth,
        val events: List<Event>,
        val daysWithEvents: List<LocalDate>,
    ) : CalendarUiState()
}
