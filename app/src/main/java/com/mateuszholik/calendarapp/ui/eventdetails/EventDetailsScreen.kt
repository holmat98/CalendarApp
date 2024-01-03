package com.mateuszholik.calendarapp.ui.eventdetails

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Info
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUserAction.DeleteEvent
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUserAction.EnterEditMode
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.EVENT_DETAILS
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.ChangeSystemBarColors
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.domain.models.EventDetails
import com.mateuszholik.domain.models.GoogleMeet
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

@Composable
fun EventDetailsScreen(
    onBackPressed: () -> Unit,
    viewModel: EventDetailsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ChangeSystemBarColors()

    if (uiState is EventDetailsViewModel.EventDetailsUiState.ViewMode) {
        ViewModeContent(
            eventDetails = (uiState as EventDetailsViewModel.EventDetailsUiState.ViewMode).eventDetails,
            onBackPressed = onBackPressed,
            onEditPressed = { viewModel.performUserAction(EnterEditMode) },
            onDeletePressed = { viewModel.performUserAction(DeleteEvent) }
        )
    }

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
    val context = LocalContext.current

    CommonScaffold(
        navigationIcon = {
            CommonIconButton(imageVector = Icons.Default.Close, onClick = onBackPressed)
        },
        actions = {
            if (eventDetails.canModify) {
                CommonIconButton(imageVector = Icons.Default.Edit, onClick = onEditPressed)
                CommonIconButton(imageVector = Icons.Default.Delete, onClick = onDeletePressed)
            }
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

            if (eventDetails.description.description.isNotEmpty()) {
                item {
                    TitleMediumText(text = eventDetails.description.description)
                }

                if (eventDetails.description is GoogleMeet) {
                    val googleMeetDescription = eventDetails.description as GoogleMeet

                    item {
                        TextWithIcon(
                            modifier = Modifier.clickable {
                                context.startActivity(getUrlIntent(googleMeetDescription.meetingUrl))
                            },
                            text = stringResource(R.string.event_details_join_in_google_meets),
                            icon = Icons.Outlined.Call
                        )
                    }

                    if (googleMeetDescription.otherUrls.isNotEmpty()) {
                        item {
                            Section(
                                sectionIcon = Icons.Outlined.Info,
                                sectionTitle = stringResource(R.string.event_details_urls),
                                items = googleMeetDescription.otherUrls
                            ) { url ->
                                TitleSmallText(
                                    modifier = Modifier
                                        .padding(top = MaterialTheme.spacing.small)
                                        .clickable { context.startActivity(getUrlIntent(url)) },
                                    text = url
                                )
                            }
                        }
                    }
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

            if (eventDetails.location.isNotEmpty()) {
                item {
                    TextWithIcon(
                        text = eventDetails.location,
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
                            text = pluralStringResource(
                                id = R.plurals.event_details_alert_minutes_before,
                                count = alert.minutesBefore.toInt(),
                                alert.minutesBefore
                            )
                        )
                    }
                }
            }

            item {
                Section(
                    sectionIcon = Icons.Outlined.Person,
                    sectionTitle = stringResource(R.string.event_details_organizer),
                    items = listOf(eventDetails.organizer)
                ) { organizer ->
                    TitleMediumText(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                        text = organizer
                    )
                }
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
                            name = attendee.email,
                            status = Status.valueOf(attendee.status.name)
                        )
                    }
                }
            }
        }
    }
}

private fun getUrlIntent(url: String): Intent =
    Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }

@SmallPhonePreview
@Composable
private fun ViewModePreview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        ViewModeContent(
            eventDetails = EVENT_DETAILS,
            onBackPressed = {},
            onEditPressed = {},
            onDeletePressed = {}
        )
    }
}

@MediumPhonePreview
@Composable
private fun ViewModePreview2() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        ViewModeContent(
            eventDetails = EVENT_DETAILS.copy(eventColor = null),
            onBackPressed = {},
            onEditPressed = {},
            onDeletePressed = {}
        )
    }
}

@BigPhonePreview
@Composable
private fun ViewModePreview3() {
    CalendarAppTheme(styleType = StyleType.SUMMER, darkTheme = true) {
        ViewModeContent(
            eventDetails = EVENT_DETAILS.copy(eventColor = Color.Yellow.toArgb(), canModify = false),
            onBackPressed = {},
            onEditPressed = {},
            onDeletePressed = {}
        )
    }
}
