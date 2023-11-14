package com.mateuszholik.calendarapp.ui.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.extensions.toYearMonth
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiState.CalendarInfo
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiState.Loading
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUserAction.CurrentMonthChanged
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUserAction.EventClicked
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUserAction.SelectedDateChanged
import com.mateuszholik.calendarapp.ui.observers.ObserveAsEvents
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.CURRENT_DATE
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.DAYS_WITH_EVENTS
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.EVENTS
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.calendar.CalendarView
import com.mateuszholik.uicomponents.event.EventItem
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarScreen(
    onEventClicked: (eventId: Long) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            CalendarViewModel.CalendarUiEvent.Error -> {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        message = "Something went wrong",
                        withDismissAction = true,
                    )
                }
            }
            is CalendarViewModel.CalendarUiEvent.NavigateToEvent -> onEventClicked(event.eventId)
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.secondary,
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { paddingValues ->
        when (uiState) {
            is CalendarInfo -> {
                val info = uiState as CalendarInfo
                Content(
                    paddingValues = paddingValues,
                    calendarInfo = info,
                    onDateChanged = { newDate ->
                        viewModel.performUserAction(SelectedDateChanged(newDate = newDate))
                    },
                    onMonthChanged = { newMonth ->
                        viewModel.performUserAction(CurrentMonthChanged(newMonth))
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
    onMonthChanged: (month: YearMonth) -> Unit,
    onEventClicked: (eventId: Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            CalendarView(
                currentDay = calendarInfo.currentDate,
                currentMonth = calendarInfo.currentMonth,
                daysWithEvents = calendarInfo.daysWithEvents,
                onDateChanged = onDateChanged,
                onMonthChanged = onMonthChanged,
            )
        }

        if (calendarInfo.events.isEmpty()) {
            item {
                Image(
                    modifier = Modifier
                        .size(MaterialTheme.sizing.big)
                        .padding(top = MaterialTheme.spacing.normal),
                    painter = painterResource(R.drawable.ic_empty),
                    contentDescription = null
                )
            }
        } else {
            items(
                items = calendarInfo.events,
                key = { event -> event.id }
            ) {
                EventItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.normal),
                    title = it.title,
                    startTime = it.startDate,
                    endTime = it.endDate,
                    onEventClicked = { onEventClicked(it.id) }
                )
            }
        }
    }
}

@SmallPhonePreview
@Composable
private fun SmallPhonePreview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        Surface(color = MaterialTheme.colorScheme.secondary) {
            Content(
                paddingValues = PaddingValues(MaterialTheme.spacing.normal),
                calendarInfo = CalendarInfo(
                    currentDate = CURRENT_DATE,
                    currentMonth = CURRENT_DATE.toYearMonth(),
                    daysWithEvents = DAYS_WITH_EVENTS,
                    events = EVENTS
                ),
                onDateChanged = {},
                onMonthChanged = {},
                onEventClicked = {}
            )
        }
    }
}

@SmallPhonePreview
@Composable
private fun SmallPhonePreview2() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(color = MaterialTheme.colorScheme.secondary) {
            Content(
                paddingValues = PaddingValues(MaterialTheme.spacing.normal),
                calendarInfo = CalendarInfo(
                    currentDate = CURRENT_DATE,
                    currentMonth = CURRENT_DATE.toYearMonth(),
                    daysWithEvents = DAYS_WITH_EVENTS,
                    events = emptyList()
                ),
                onDateChanged = {},
                onMonthChanged = {},
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
                paddingValues = PaddingValues(MaterialTheme.spacing.normal),
                calendarInfo = CalendarInfo(
                    currentDate = CURRENT_DATE,
                    currentMonth = CURRENT_DATE.toYearMonth(),
                    daysWithEvents = DAYS_WITH_EVENTS,
                    events = EVENTS
                ),
                onDateChanged = {},
                onMonthChanged = {},
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
                paddingValues = PaddingValues(MaterialTheme.spacing.normal),
                calendarInfo = CalendarInfo(
                    currentDate = CURRENT_DATE,
                    currentMonth = CURRENT_DATE.toYearMonth(),
                    daysWithEvents = DAYS_WITH_EVENTS,
                    events = EVENTS
                ),
                onDateChanged = {},
                onMonthChanged = {},
                onEventClicked = {}
            )
        }
    }
}
