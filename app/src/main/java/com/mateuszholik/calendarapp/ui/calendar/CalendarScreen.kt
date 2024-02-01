package com.mateuszholik.calendarapp.ui.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiEvent.Error
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiEvent.NavigateToAddEvent
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiEvent.NavigateToEvent
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiEvent.NavigateToCalendarsSelection
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiState.CalendarInfo
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiState.Loading
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUserAction.CurrentMonthChanged
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUserAction.AddEventClicked
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUserAction.EventClicked
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUserAction.SelectedDateChanged
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUserAction.ProfileClicked
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUserAction.RefreshScreen
import com.mateuszholik.calendarapp.ui.observers.ObserveAsEvents
import com.mateuszholik.calendarapp.ui.observers.ObserveResumeLifecycleState
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.CURRENT_DATE
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.DAYS_WITH_EVENTS
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.EVENTS
import com.mateuszholik.dateutils.extensions.toYearMonth
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.ChangeSystemBarColors
import com.mateuszholik.designsystem.cornerRadius
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.buttons.CommonIconButton
import com.mateuszholik.uicomponents.calendar.CalendarView
import com.mateuszholik.uicomponents.event.EventItem
import com.mateuszholik.uicomponents.extensions.shimmerEffect
import com.mateuszholik.uicomponents.scaffold.CommonScaffold
import com.mateuszholik.uicomponents.scaffold.CommonScaffoldDefaults
import com.mateuszholik.uicomponents.text.HeadlineSmallText
import com.mateuszholik.uicomponents.text.TitleSmallText
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    onAddEventClicked: () -> Unit,
    onEventClicked: (eventId: Long) -> Unit,
    onProfileClicked: () -> Unit,
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    ChangeSystemBarColors(areIconsDark = isSystemInDarkTheme())

    ObserveResumeLifecycleState {
        viewModel.performUserAction(RefreshScreen)
    }

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is Error -> {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        message = context.getString(R.string.error_unknown_text),
                        withDismissAction = true,
                    )
                }
            }
            is NavigateToAddEvent -> onAddEventClicked()
            is NavigateToEvent -> onEventClicked(event.eventId)
            is NavigateToCalendarsSelection -> onProfileClicked()
        }
    }

    CommonScaffold(
        colors = CommonScaffoldDefaults.colors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            topBarContainerColor = MaterialTheme.colorScheme.secondary,
            topBarContentColor = MaterialTheme.colorScheme.onSecondary
        ),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        actions = {
            CommonIconButton(
                imageVector = Icons.Filled.AccountCircle,
                onClick = { viewModel.performUserAction(ProfileClicked) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = { viewModel.performUserAction(AddEventClicked) }
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {
        val paddingValues = PaddingValues(
            top = it.calculateTopPadding(),
            start = MaterialTheme.spacing.normal,
            bottom = it.calculateBottomPadding(),
            end = MaterialTheme.spacing.normal,
        )

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
            is Loading -> {
                val loading = uiState as Loading
                ShimmerContent(
                    currentMonth = loading.currentMonth,
                    currentDate = loading.currentDate,
                    paddingValues = paddingValues
                )
            }
        }
    }
}

@Composable
private fun ShimmerContent(
    currentMonth: YearMonth,
    currentDate: LocalDate,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.normal),
    ) {
        item {
            CalendarView(
                currentDay = currentDate,
                currentMonth = currentMonth,
                daysWithEvents = emptyList(),
                onDateChanged = {},
                onMonthChanged = {},
            )
        }
        items(count = 2) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.sizing.big)
                    .clip(RoundedCornerShape(MaterialTheme.cornerRadius.normal))
                    .shimmerEffect()
            )
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.normal),
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
                        .padding(top = MaterialTheme.spacing.normal)
                        .size(MaterialTheme.sizing.extraBig),
                    painter = painterResource(R.drawable.ic_empty),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
            item {
                HeadlineSmallText(
                    textResId = R.string.calendar_screen_no_events_title,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
            item {
                TitleSmallText(
                    textResId = R.string.calendar_screen_no_events_message,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            items(
                items = calendarInfo.events,
                key = { event -> event.id }
            ) {
                EventItem(
                    modifier = Modifier.fillMaxWidth(),
                    title = it.title,
                    startTime = it.startDate,
                    endTime = it.endDate,
                    allDay = it.allDay,
                    color = it.color,
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

@SmallPhonePreview
@Composable
private fun SmallPhoneLoadingPreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(color = MaterialTheme.colorScheme.secondary) {
            ShimmerContent(
                paddingValues = PaddingValues(MaterialTheme.spacing.normal),
                currentMonth = CURRENT_DATE.toYearMonth(),
                currentDate = CURRENT_DATE
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

@MediumPhonePreview
@Composable
private fun MediumPhonePreview2() {
    CalendarAppTheme(styleType = StyleType.WINTER, darkTheme = true) {
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

@BigPhonePreview
@Composable
private fun BigPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SUMMER, darkTheme = true) {
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
private fun BigPhonePreview2() {
    CalendarAppTheme(styleType = StyleType.SUMMER) {
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
