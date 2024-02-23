package com.mateuszholik.calendarapp.ui.addevent.models

import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.calendarapp.ui.provider.ColorsProvider
import com.mateuszholik.dateutils.Minutes
import com.mateuszholik.domain.models.Calendar
import java.time.LocalDateTime
import java.util.TimeZone

sealed class AddEventUiState : UiState {

    data class AddEventData(
        val title: String,
        val description: String,
        val allDay: Boolean,
        val startDate: LocalDateTime,
        val endDate: LocalDateTime,
        val timezone: TimeZone,
        val urls: String,
        val calendar: Calendar,
        val color: ColorsProvider.ColorInfo,
        val location: String,
        val reminder: Minutes?,
    ) : AddEventUiState()

    data object Loading : AddEventUiState()
}
