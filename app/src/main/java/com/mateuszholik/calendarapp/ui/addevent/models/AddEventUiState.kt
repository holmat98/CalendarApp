package com.mateuszholik.calendarapp.ui.addevent.models

import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.calendarapp.ui.provider.ColorsProvider
import com.mateuszholik.domain.models.Calendar
import java.time.LocalDateTime

data class AddEventUiState(
    val title: String,
    val description: String,
    val allDay: Boolean,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val timezone: String,
    val urls: String,
    val calendar: Calendar,
    val color: ColorsProvider.ColorInfo,
    val location: String,
) : UiState
