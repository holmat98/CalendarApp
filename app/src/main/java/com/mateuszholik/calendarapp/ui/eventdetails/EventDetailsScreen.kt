package com.mateuszholik.calendarapp.ui.eventdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.domain.models.Alert
import com.mateuszholik.domain.models.Attendee
import com.mateuszholik.domain.models.AttendeeStatus
import com.mateuszholik.domain.models.Availability
import com.mateuszholik.domain.models.EventDetails
import com.mateuszholik.uicomponents.Section
import com.mateuszholik.uicomponents.attendee.AttendeeItem
import com.mateuszholik.uicomponents.attendee.Status
import com.mateuszholik.uicomponents.buttons.CommonIconButton
import com.mateuszholik.uicomponents.date.EventDate
import com.mateuszholik.uicomponents.scaffold.CommonScaffold
import com.mateuszholik.uicomponents.scaffold.CommonScaffoldDefaults
import com.mateuszholik.uicomponents.text.TextWithIcon
import com.mateuszholik.uicomponents.text.TitleLargeText
import com.mateuszholik.uicomponents.text.TitleMediumText
import com.mateuszholik.uicomponents.text.TitleSmallText
import java.time.LocalDateTime

@Composable
fun EventDetailsScreen(
    onBackPressed: () -> Unit,
    viewModel: EventDetailsViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    /*when (uiState) {
        is EventDetailsViewModel.EventDetailsUiState.EditMode -> TODO()
        is EventDetailsViewModel.EventDetailsUiState.Loading -> TODO()
        is EventDetailsViewModel.EventDetailsUiState.NoData -> TODO()
        is EventDetailsViewModel.EventDetailsUiState.ViewMode -> TODO()
    }*/
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewModeContent(
    eventDetails: EventDetails,
    onBackPressed: () -> Unit,
    onEditPressed: () -> Unit,
    onDeletePressed: () -> Unit,
) {
    CommonScaffold(
        navigationIcon = {
            CommonIconButton(imageVector = Icons.Default.Close, onClick = onBackPressed)
        },
        actions = {
            if (eventDetails.canModify) {
                CommonIconButton(imageVector = Icons.Default.Edit, onClick = onEditPressed)
            }
            CommonIconButton(imageVector = Icons.Default.Delete, onClick = onDeletePressed)
        },
        colors = CommonScaffoldDefaults.colors(
            topBarContainerColor = eventDetails.eventColor?.let { Color(it) }
                ?: MaterialTheme.colorScheme.surface,
        )
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(
                    PaddingValues(
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding(),
                        end = MaterialTheme.spacing.normal,
                        start = MaterialTheme.spacing.normal
                    )
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.normal),
        ) {
            item { TitleLargeText(text = eventDetails.title) }

            eventDetails.description?.let { description ->
                item {
                    TitleMediumText(text = description)
                }
            }

            item {
                EventDate(
                    modifier = Modifier.padding(bottom = MaterialTheme.spacing.normal),
                    startDate = eventDetails.dateStart,
                    endDate = eventDetails.dateEnd,
                    allDay = eventDetails.allDay,
                )
                Divider()
            }

            eventDetails.location?.let { location ->
                item {
                    TextWithIcon(
                        text = location,
                        icon = Icons.Outlined.LocationOn
                    )
                }
            }

            if (eventDetails.alerts.isNotEmpty()) {
                item {
                    Section(
                        sectionIcon = Icons.Outlined.Notifications,
                        sectionTitle = stringResource(R.string.event_details_alerts),
                        items = eventDetails.alerts
                    ) { alert ->
                        TitleMediumText(
                            modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                            text = alert.minutesBefore
                        )
                    }
                }
            }

            item {
                TextWithIcon(
                    text = stringResource(R.string.event_details_organizer, eventDetails.organizer),
                    icon = Icons.Outlined.Person
                )
            }

            if (eventDetails.attendees.isNotEmpty()) {
                item {
                    Section(
                        sectionIcon = Icons.Outlined.Person,
                        sectionTitle = pluralStringResource(
                            R.plurals.event_details_guests,
                            eventDetails.attendees.size,
                            eventDetails.attendees.size
                        ),
                        items = eventDetails.attendees
                    ) { attendee ->
                        AttendeeItem(
                            modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                            name = attendee.name,
                            status = Status.valueOf(attendee.status.name)
                        )
                    }
                }
            }
        }
    }
}

@SmallPhonePreview
@Composable
private fun ViewModePreview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        ViewModeContent(
            eventDetails = EventDetails(
                id = 1L,
                title = "Event title",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec mollis erat in libero posuere mattis. Aenean dapibus risus consequat, faucibus est at, vestibulum ipsum. Phasellus a elit id nisl euismod rhoncus. Aenean accumsan eget ante et dignissim. Proin non purus vel lacus facilisis facilisis.",
                alerts = listOf(Alert("30")),
                allDay = false,
                dateStart = LocalDateTime.of(2023, 12, 31, 12, 0, 0),
                dateEnd = LocalDateTime.of(2023, 12, 31, 13, 30, 0),
                attendees = listOf(
                    Attendee(
                        1,
                        name = "Attendee 1",
                        email = "email",
                        status = AttendeeStatus.ACCEPTED
                    ),
                    Attendee(
                        2,
                        name = "Attendee 2",
                        email = "email",
                        status = AttendeeStatus.DECLINED
                    ),
                    Attendee(
                        3,
                        name = "Attendee 3",
                        email = "email",
                        status = AttendeeStatus.INVITED
                    ),
                ),
                availability = Availability.FREE,
                canModify = true,
                eventColor = Color.Green.toArgb(),
                location = "Zabrze",
                organizer = "Organizer",
            ),
            onBackPressed = {},
            onEditPressed = {},
            onDeletePressed = {}
        )
    }
}
