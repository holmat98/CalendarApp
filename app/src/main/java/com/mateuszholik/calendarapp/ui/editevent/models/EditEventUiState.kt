package com.mateuszholik.calendarapp.ui.editevent.models

import androidx.compose.runtime.Stable
import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.calendarapp.ui.provider.ColorsProvider
import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.domain.models.Description
import java.time.LocalDateTime

@Stable
sealed class EditEventUiState : UiState {

    data object Loading : EditEventUiState()

    data class EventDetails(
        val id: Long,
        val title: String,
        val description: Description,
        val dateStart: LocalDateTime,
        val dateEnd: LocalDateTime,
        val timezone: String,
        val allDay: Boolean,
        val eventColor: ColorsProvider.ColorInfo?,
        val location: String,
        val calendar: Calendar?,
    ) : EditEventUiState()
}
