package com.mateuszholik.calendarapp.ui.calendar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiState.CalendarInfo
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiState.Loading
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUserAction.EventClicked
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUserAction.SelectedDateChanged
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.CURRENT_DATE
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.DAYS_WITH_EVENTS
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.EVENTS
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.uicomponents.calendar.CalendarView
import com.mateuszholik.uicomponents.event.EventItem
import com.mateuszholik.uicomponents.text.HeadlineMediumText
import java.time.LocalDate

@Composable
fun CalendarScreen(
    onEventClicked: (eventId: Long) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.secondary
    ) { paddingValues ->
        when (uiState) {
            is CalendarInfo -> {
                val info = uiState as CalendarInfo
                Content(
                    paddingValues = paddingValues,
                    calendarInfo = info,
                    onDateChanged = { newDate ->
                        viewModel.performUserAction(
                            SelectedDateChanged(
                                newDate = newDate
                            )
                        )
                    },
                    onEventClicked = { eventId ->
                        viewModel.performUserAction(EventClicked(eventId))
                    }
                )
            }
            Loading -> TODO()
        }

    }
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    calendarInfo: CalendarInfo,
    onDateChanged: (date: LocalDate) -> Unit,
    onEventClicked: (eventId: Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        item {
            CalendarView(
                currentDay = calendarInfo.currentDate,
                daysWithEvents = calendarInfo.daysWithEvents,
                onDateChanged = onDateChanged
            )
        }

        items(
            items = calendarInfo.events,
            key = { event -> event.id }
        ) {
            EventItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                title = it.title,
                startTime = it.startDate,
                endTime = it.endDate,
                onEventClicked = { onEventClicked(it.id) }
            )
        }
    }
}

@SmallPhonePreview
@Composable
private fun SmallPhonePreview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        Surface(color = MaterialTheme.colorScheme.secondary) {
            Content(
                paddingValues = PaddingValues(16.dp),
                calendarInfo = CalendarInfo(
                    currentDate = CURRENT_DATE,
                    daysWithEvents = DAYS_WITH_EVENTS,
                    events = EVENTS
                ),
                onDateChanged = {},
                onEventClicked = {}
            )
        }
    }
}

@MediumPhonePreview
@Composable
private fun MediumPhonePreview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        Surface(color = MaterialTheme.colorScheme.secondary) {
            Content(
                paddingValues = PaddingValues(24.dp),
                calendarInfo = CalendarInfo(
                    currentDate = CURRENT_DATE,
                    daysWithEvents = DAYS_WITH_EVENTS,
                    events = EVENTS
                ),
                onDateChanged = {},
                onEventClicked = {}
            )
        }
    }
}

@BigPhonePreview
@Composable
private fun BigPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SUMMER) {
        Surface(color = MaterialTheme.colorScheme.secondary) {
            Content(
                paddingValues = PaddingValues(32.dp),
                calendarInfo = CalendarInfo(
                    currentDate = CURRENT_DATE,
                    daysWithEvents = DAYS_WITH_EVENTS,
                    events = EVENTS
                ),
                onDateChanged = {},
                onEventClicked = {}
            )
        }
    }
}
