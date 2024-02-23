package com.mateuszholik.calendarapp.ui.addevent

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.extensions.asText
import com.mateuszholik.calendarapp.ui.addevent.models.AddEventUiEvent
import com.mateuszholik.calendarapp.ui.addevent.models.AddEventUiState
import com.mateuszholik.calendarapp.ui.addevent.models.AddEventUserAction
import com.mateuszholik.calendarapp.ui.addevent.models.AddEventUserAction.AllDaySelectionChanged
import com.mateuszholik.calendarapp.ui.observers.ObserveAsEvents
import com.mateuszholik.calendarapp.ui.provider.ColorsProvider
import com.mateuszholik.dateutils.Minutes
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.ChangeSystemBarColors
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.uicomponents.buttons.CommonIconButton
import com.mateuszholik.uicomponents.buttons.CommonSmallButton
import com.mateuszholik.uicomponents.calendar.CalendarItem
import com.mateuszholik.uicomponents.cards.SelectionCard
import com.mateuszholik.uicomponents.color.ColorItem
import com.mateuszholik.uicomponents.date.CommonDatePicker
import com.mateuszholik.uicomponents.date.CommonDateTimePicker
import com.mateuszholik.uicomponents.date.TimezoneItem
import com.mateuszholik.uicomponents.dialog.CommonDialog
import com.mateuszholik.uicomponents.dialog.CommonSearchDialog
import com.mateuszholik.uicomponents.scaffold.CommonScaffold
import com.mateuszholik.uicomponents.switches.CommonSwitch
import com.mateuszholik.uicomponents.text.BodySmallText
import com.mateuszholik.uicomponents.text.TextWithIcon
import com.mateuszholik.uicomponents.text.TitleMediumText
import com.mateuszholik.uicomponents.textfield.CommonOutlinedTextField
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    onBackPressed: () -> Unit,
    viewModel: AddEventScreenViewModel = hiltViewModel(),
) {
    var calendars by remember { mutableStateOf<List<Calendar>?>(null) }
    var colors by remember { mutableStateOf<List<ColorsProvider.ColorInfo>?>(null) }
    var timezones by remember { mutableStateOf<List<TimeZone>?>(null) }
    var reminders by remember { mutableStateOf<List<Minutes>?>(null) }
    val isButtonEnabled by remember { mutableStateOf(true) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    ChangeSystemBarColors()

    ObserveAsEvents(flow = viewModel.uiEvent) { uiEvent ->
        when (uiEvent) {
            is AddEventUiEvent.DismissCalendarSelection -> {
                calendars = null
            }
            is AddEventUiEvent.DismissColorEventSelection -> {
                colors = null
            }
            is AddEventUiEvent.DismissTimeZoneSelection -> {
                timezones = null
            }
            is AddEventUiEvent.Error -> {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        message = context.getString(R.string.error_unknown_text),
                        withDismissAction = true,
                    )
                }
            }
            is AddEventUiEvent.NavigateBack -> {
                onBackPressed()
            }
            is AddEventUiEvent.ShowCalendarSelection -> {
                calendars = uiEvent.calendars
            }
            is AddEventUiEvent.ShowEventColorSelection -> {
                colors = uiEvent.colors
            }
            is AddEventUiEvent.ShowTimeZoneSelection -> {
                timezones = uiEvent.timezones
            }
            is AddEventUiEvent.ShowReminderSelection -> {
                reminders = uiEvent.minutes
            }
            is AddEventUiEvent.DismissReminderSelection -> {
                reminders = null
            }
        }
    }

    CommonScaffold(
        navigationIcon = {
            CommonIconButton(
                imageVector = Icons.Default.ArrowBack,
                onClick = { viewModel.performUserAction(AddEventUserAction.ExitAttempt) },
            )
        },
        actions = {
            CommonSmallButton(
                modifier = Modifier.padding(end = MaterialTheme.spacing.small),
                textResId = R.string.button_save,
                isEnabled = isButtonEnabled,
                onClick = { viewModel.performUserAction(AddEventUserAction.SaveEvent) }
            )
        }
    ) {
        val paddingValues = PaddingValues(
            start = MaterialTheme.spacing.normal,
            end = MaterialTheme.spacing.normal,
            top = it.calculateTopPadding(),
            bottom = it.calculateBottomPadding(),
        )

        when (uiState) {
            is AddEventUiState.AddEventData -> {
                Content(
                    paddingValues = paddingValues,
                    addEventData = uiState as AddEventUiState.AddEventData,
                    onAllDayChanged = { allDay ->
                        viewModel.performUserAction(
                            AllDaySelectionChanged(allDay)
                        )
                    },
                    onCalendarPressed = { viewModel.performUserAction(AddEventUserAction.SelectCalendar) },
                    onColorPressed = { viewModel.performUserAction(AddEventUserAction.SelectEventColor) },
                    onTitleChanged = { newTitle ->
                        viewModel.performUserAction(
                            AddEventUserAction.UpdateTitle(newTitle)
                        )
                    },
                    onDescriptionChanged = { newDescription ->
                        viewModel.performUserAction(AddEventUserAction.UpdateDescription(newDescription))
                    },
                    onLocationChanged = { newLocation ->
                        viewModel.performUserAction(AddEventUserAction.UpdateLocation(newLocation))
                    },
                    onStartDateChanged = { newStartDate ->
                        viewModel.performUserAction(AddEventUserAction.StartDateChanged(newStartDate))
                    },
                    onEndDateChanged = { newEndDate ->
                        viewModel.performUserAction(AddEventUserAction.EndDateChanged(newEndDate))
                    },
                    onTimeZonePressed = { viewModel.performUserAction(AddEventUserAction.SelectTimeZone) },
                    onUrlsChanged = { newUrls ->
                        viewModel.performUserAction(AddEventUserAction.UpdateUrls(newUrls))
                    },
                    onReminderPressed = { viewModel.performUserAction(AddEventUserAction.SelectReminder) },
                )
            }
            is AddEventUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

    calendars?.let {
        CommonDialog(
            onDismissRequest = {
                viewModel.performUserAction(AddEventUserAction.CalendarSelectionDismissed)
            }
        ) {
            item {
                TitleMediumText(textResId = R.string.edit_event_select_calendar)
            }
            items(items = it) { calendar ->
                with(calendar) {
                    CalendarItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.performUserAction(
                                    AddEventUserAction.CalendarSelected(calendar)
                                )
                            },
                        email = accountName,
                        name = calendarName,
                        calendarColor = color
                    )
                }
            }
        }
    }

    colors?.let {
        CommonDialog(
            onDismissRequest = {
                viewModel.performUserAction(AddEventUserAction.SelectEventColorDismissed)
            }
        ) {
            item {
                TitleMediumText(textResId = R.string.edit_event_select_event_color)
            }

            items(items = it) { color ->
                ColorItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.performUserAction(
                                AddEventUserAction.SelectedEventColor(color)
                            )
                        },
                    color = color.value,
                    name = stringResource(color.name)
                )
            }
        }
    }

    timezones?.let {
        CommonSearchDialog(
            items = it,
            title = stringResource(R.string.add_event_select_timezone),
            searchHint = stringResource(R.string.add_event_search_for_timezone),
            onDismissRequest = { viewModel.performUserAction(AddEventUserAction.SelectTimeZoneDismissed) },
            predicate = { timezone, searchedText -> timezone.id.contains(searchedText, true) },
            onEmptySearchContent = {
                BodySmallText(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.add_event_search_timezone_not_found),
                    textAlign = TextAlign.Center,
                )
            }
        ) { timezone ->
            TimezoneItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.performUserAction(
                            AddEventUserAction.TimeZoneSelected(timezone)
                        )
                    },
                timezone = timezone
            )
        }
    }

    reminders?.let {
        CommonDialog(
            onDismissRequest = { viewModel.performUserAction(AddEventUserAction.SelectReminderDismissed) }
        ) {
            item {
                TitleMediumText(textResId = R.string.add_event_select_reminder)
            }

            items(items = it) { minutes ->
                TitleMediumText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.performUserAction(
                                AddEventUserAction.ReminderSelected(minutes)
                            )
                        },
                    text = minutes.asText(context)
                )
            }
        }
    }

}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    addEventData: AddEventUiState.AddEventData,
    onCalendarPressed: () -> Unit,
    onColorPressed: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onStartDateChanged: (LocalDateTime) -> Unit = {},
    onEndDateChanged: (LocalDateTime) -> Unit = {},
    onAllDayChanged: (Boolean) -> Unit = {},
    onTimeZonePressed: () -> Unit,
    onUrlsChanged: (String) -> Unit,
    onReminderPressed: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.normal),
        contentPadding = PaddingValues(vertical = MaterialTheme.spacing.normal),
    ) {
        item {
            CommonOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = addEventData.title,
                onTextChanged = onTitleChanged,
                hint = stringResource(R.string.edit_event_provide_title),
                focusRequester = focusRequester,
                singleLine = true
            )
        }

        item {
            SelectionCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onCalendarPressed()
                    focusManager.clearFocus()
                }
            ) {
                with(addEventData.calendar) {
                    CalendarItem(
                        email = accountName,
                        name = calendarName,
                        calendarColor = color
                    )
                }
            }
        }

        item { Divider() }

        item {
            Column(modifier = Modifier.animateContentSize()) {
                CommonSwitch(
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.spacing.small)
                        .fillMaxWidth(),
                    text = stringResource(R.string.edit_event_all_day),
                    isSelected = addEventData.allDay,
                    onSelectionChanged = {
                        onAllDayChanged(it)
                        focusManager.clearFocus()
                    }
                )
                if (addEventData.allDay) {
                    CommonDatePicker(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.small)
                            .fillMaxWidth(),
                        date = addEventData.startDate.toLocalDate(),
                        onDateSelected = {
                            onStartDateChanged(it.atStartOfDay())
                            focusManager.clearFocus()
                        }
                    )
                    CommonDatePicker(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.small)
                            .fillMaxWidth(),
                        date = addEventData.endDate.toLocalDate(),
                        onDateSelected = {
                            onEndDateChanged(it.atStartOfDay())
                            focusManager.clearFocus()
                        }
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.padding(end = MaterialTheme.spacing.small),
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null
                        )
                        TimezoneItem(timezone = addEventData.timezone)
                    }
                } else {
                    CommonDateTimePicker(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.small)
                            .fillMaxWidth(),
                        date = addEventData.startDate,
                        onDateSelected = {
                            onStartDateChanged(it)
                            focusManager.clearFocus()
                        }
                    )
                    CommonDateTimePicker(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.small)
                            .fillMaxWidth(),
                        date = addEventData.endDate,
                        onDateSelected = {
                            onEndDateChanged(it)
                            focusManager.clearFocus()
                        }
                    )
                    SelectionCard(
                        onClick = {
                            onTimeZonePressed()
                            focusManager.clearFocus()
                        }
                    ) {
                        TimezoneItem(timezone = addEventData.timezone)
                    }
                }
            }
        }

        item { Divider() }

        item {
            val context = LocalContext.current
            SelectionCard(
                onClick = {
                    onReminderPressed()
                    focusManager.clearFocus()
                }
            ) {
                TextWithIcon(
                    text = if (addEventData.reminder == null) {
                        stringResource(R.string.add_event_select_reminder)
                    } else {
                        addEventData.reminder.asText(context)
                    },
                    icon = Icons.Outlined.Notifications
                )
            }
        }

        item {
            CommonOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = addEventData.description,
                onTextChanged = onDescriptionChanged,
                hint = stringResource(R.string.edit_event_provide_description),
                focusRequester = focusRequester,
            )
        }

        item {
            SelectionCard(
                onClick = {
                    onColorPressed()
                    focusManager.clearFocus()
                }
            ) {
                ColorItem(
                    color = addEventData.color.value,
                    name = stringResource(addEventData.color.name),
                )
            }
        }

        item {
            CommonOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = addEventData.urls,
                supportingText = stringResource(R.string.add_event_provide_urls_explanation),
                onTextChanged = onUrlsChanged,
                hint = stringResource(R.string.add_event_provide_urls),
                focusRequester = focusRequester,
            )
        }

        item {
            CommonOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = addEventData.location,
                onTextChanged = onLocationChanged,
                hint = stringResource(R.string.edit_event_provide_location),
                focusRequester = focusRequester,
            )
        }
    }
}

@SmallPhonePreview
@Composable
private fun Preview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Content(
                paddingValues = PaddingValues(
                    horizontal = MaterialTheme.spacing.normal,
                    vertical = 0.dp
                ),
                addEventData = AddEventUiState.AddEventData(
                    title = "Title",
                    description = "Description",
                    allDay = false,
                    startDate = LocalDateTime.of(2024, 2, 10, 15, 0, 0),
                    endDate = LocalDateTime.of(2024, 2, 10, 16, 0, 0),
                    timezone = TimeZone.getDefault(),
                    urls = "",
                    calendar = Calendar(
                        id = 1,
                        accountName = "test",
                        calendarName = "test",
                        isVisible = true,
                        color = null
                    ),
                    color = ColorsProvider.ColorInfo(
                        value = Color.Magenta.toArgb(),
                        name = R.string.color_current,
                    ),
                    location = "Poland",
                    reminder = Minutes.from(45),
                ),
                onAllDayChanged = {},
                onStartDateChanged = {},
                onCalendarPressed = {},
                onColorPressed = {},
                onTitleChanged = {},
                onDescriptionChanged = {},
                onLocationChanged = {},
                onTimeZonePressed = {},
                onUrlsChanged = {},
                onReminderPressed = {},
            )
        }
    }
}

@MediumPhonePreview
@Composable
private fun Preview2() {
    CalendarAppTheme(styleType = StyleType.SUMMER) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Content(
                paddingValues = PaddingValues(
                    horizontal = MaterialTheme.spacing.normal,
                    vertical = 0.dp
                ),
                addEventData = AddEventUiState.AddEventData(
                    title = "Title",
                    description = "Description",
                    allDay = false,
                    startDate = LocalDateTime.of(2024, 2, 10, 15, 0, 0),
                    endDate = LocalDateTime.of(2024, 2, 10, 16, 0, 0),
                    timezone = TimeZone.getDefault(),
                    urls = "",
                    calendar = Calendar(
                        id = 1,
                        accountName = "test",
                        calendarName = "test",
                        isVisible = true,
                        color = null
                    ),
                    color = ColorsProvider.ColorInfo(
                        value = Color.Magenta.toArgb(),
                        name = R.string.color_current,
                    ),
                    location = "Poland",
                    reminder = null,
                ),
                onAllDayChanged = {},
                onStartDateChanged = {},
                onCalendarPressed = {},
                onColorPressed = {},
                onTitleChanged = {},
                onDescriptionChanged = {},
                onLocationChanged = {},
                onTimeZonePressed = {},
                onUrlsChanged = {},
                onReminderPressed = {},
            )
        }
    }
}

@BigPhonePreview
@Composable
private fun Preview3() {
    CalendarAppTheme(styleType = StyleType.WINTER, darkTheme = true) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Content(
                paddingValues = PaddingValues(
                    horizontal = MaterialTheme.spacing.normal,
                    vertical = 0.dp
                ),
                addEventData = AddEventUiState.AddEventData(
                    title = "Title",
                    description = "Description",
                    allDay = true,
                    startDate = LocalDateTime.of(2024, 2, 10, 0, 0, 0),
                    endDate = LocalDateTime.of(2024, 2, 10, 0, 0, 0),
                    timezone = TimeZone.getDefault(),
                    urls = "",
                    calendar = Calendar(
                        id = 1,
                        accountName = "test",
                        calendarName = "test",
                        isVisible = true,
                        color = null
                    ),
                    color = ColorsProvider.ColorInfo(
                        value = Color.Magenta.toArgb(),
                        name = R.string.color_current,
                    ),
                    location = "Poland",
                    reminder = Minutes.fromDays(1),
                ),
                onAllDayChanged = {},
                onStartDateChanged = {},
                onCalendarPressed = {},
                onColorPressed = {},
                onTitleChanged = {},
                onDescriptionChanged = {},
                onLocationChanged = {},
                onTimeZonePressed = {},
                onUrlsChanged = {},
                onReminderPressed = {},
            )
        }
    }
}
