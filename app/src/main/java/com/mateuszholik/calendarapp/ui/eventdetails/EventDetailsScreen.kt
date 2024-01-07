package com.mateuszholik.calendarapp.ui.eventdetails

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUiEvent
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUserAction.AttendeeDismissed
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUserAction.AttendeeSelected
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUserAction.DeleteEvent
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUserAction.EnterEditMode
import com.mateuszholik.calendarapp.ui.observers.ObserveAsEvents
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.EVENT_DETAILS
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.EVENT_DETAILS_EMPTY_DESCRIPTION
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.EVENT_DETAILS_GENERIC_DESCRIPTION
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.ChangeSystemBarColors
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.domain.models.Attendee
import com.mateuszholik.domain.models.EventDetails
import com.mateuszholik.domain.models.Generic
import com.mateuszholik.domain.models.GoogleMeet
import com.mateuszholik.uicomponents.cards.SectionCard
import com.mateuszholik.uicomponents.attendee.AttendeeItem
import com.mateuszholik.uicomponents.attendee.Status
import com.mateuszholik.uicomponents.bottomsheet.CommonBottomSheetDialog
import com.mateuszholik.uicomponents.buttons.CommonIconButton
import com.mateuszholik.uicomponents.buttons.CommonOutlinedButton
import com.mateuszholik.uicomponents.cards.CardWithImage
import com.mateuszholik.uicomponents.cards.EventCard
import com.mateuszholik.uicomponents.cards.EventWithMeetingCard
import com.mateuszholik.uicomponents.scaffold.CommonScaffold
import com.mateuszholik.uicomponents.text.BodyMediumText
import com.mateuszholik.uicomponents.text.TitleMediumText

@Composable
fun EventDetailsScreen(
    onBackPressed: () -> Unit,
    viewModel: EventDetailsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var selectedAttendee by remember { mutableStateOf<Attendee?>(null) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ChangeSystemBarColors()

    ObserveAsEvents(viewModel.uiEvent) { uiEvent ->
        when (uiEvent) {
            is EventDetailsUiEvent.DismissAttendee -> {
                selectedAttendee = null
            }
            is EventDetailsUiEvent.Error -> {}
            is EventDetailsUiEvent.ShowAttendee -> {
                selectedAttendee = uiEvent.attendee
            }
        }
    }

    if (uiState is EventDetailsViewModel.EventDetailsUiState.ViewMode) {
        ViewModeContent(
            eventDetails = (uiState as EventDetailsViewModel.EventDetailsUiState.ViewMode).eventDetails,
            onBackPressed = onBackPressed,
            onEditPressed = { viewModel.performUserAction(EnterEditMode) },
            onDeletePressed = { viewModel.performUserAction(DeleteEvent) },
            onAttendeePressed = { viewModel.performUserAction(AttendeeSelected(it)) }
        )
    }

    selectedAttendee?.let { attendee ->
        CommonBottomSheetDialog(
            onDismissRequest = { viewModel.performUserAction(AttendeeDismissed) }
        ) {
            Icon(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.spacing.small)
                    .size(MaterialTheme.sizing.big)
                    .align(Alignment.CenterHorizontally),
                imageVector = Icons.Outlined.Face,
                contentDescription = null
            )

            TitleMediumText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = attendee.email,
                fontWeight = FontWeight.SemiBold
            )

            CommonOutlinedButton(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.spacing.small)
                    .align(Alignment.CenterHorizontally),
                textResId = R.string.event_details_send_email,
                icon = Icons.Outlined.Email,
                onClick = { context.startActivity(getMailIntent(attendee.email)) }
            )
        }
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
    onAttendeePressed: (attendee: Attendee) -> Unit,
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
            contentPadding = PaddingValues(vertical = MaterialTheme.spacing.normal)
        ) {
            when (eventDetails.description) {
                is Generic -> {
                    item {
                        EventCard(
                            modifier = Modifier.fillMaxWidth(),
                            title = eventDetails.title,
                            description = eventDetails.description.description,
                            eventStart = eventDetails.dateStart,
                            eventEnd = eventDetails.dateEnd,
                            allDay = eventDetails.allDay,
                            eventColor = eventDetails.eventColor?.let { color -> Color(color) },
                        )
                    }
                }
                is GoogleMeet -> {
                    val googleMeetDescription = eventDetails.description as GoogleMeet

                    item {
                        EventWithMeetingCard(
                            modifier = Modifier.fillMaxWidth(),
                            title = eventDetails.title,
                            description = eventDetails.description.description,
                            eventStart = eventDetails.dateStart,
                            eventEnd = eventDetails.dateEnd,
                            allDay = eventDetails.allDay,
                            eventColor = eventDetails.eventColor?.let { color -> Color(color) },
                            joinMeetingButtonText = stringResource(R.string.event_details_join_in_google_meets),
                            onJoinMeetingButtonClick = {
                                context.startActivity(getUrlIntent(googleMeetDescription.meetingUrl))
                            }
                        )
                    }

                    item {
                        SectionCard(
                            sectionIcon = Icons.Outlined.Info,
                            sectionTitle = stringResource(R.string.event_details_urls),
                            items = googleMeetDescription.otherUrls,
                        ) { url, modifier ->

                            Row(
                                modifier = modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                BodyMediumText(
                                    modifier = Modifier.weight(1f),
                                    text = url,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                CommonIconButton(
                                    imageVector = Icons.Default.ExitToApp,
                                    onClick = { context.startActivity(getUrlIntent(url)) }
                                )
                            }
                        }
                    }
                }
            }

            if (eventDetails.location.isNotEmpty()) {
                item {
                    CardWithImage(
                        modifier = Modifier.clickable {
                            context.startActivity(
                                getMapsIntent(eventDetails.location)
                            )
                        },
                        text = eventDetails.location,
                        image = R.drawable.location_background,
                        smallIcon = Icons.Outlined.LocationOn
                    )
                }
            }

            item { Divider() }

            if (eventDetails.alerts.isNotEmpty()) {
                item {
                    SectionCard(
                        sectionIcon = Icons.Outlined.Notifications,
                        sectionTitle = stringResource(R.string.event_details_alerts),
                        items = eventDetails.alerts
                    ) { alert, modifier ->
                        TitleMediumText(
                            modifier = modifier,
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
                SectionCard(
                    sectionIcon = Icons.Outlined.Person,
                    sectionTitle = stringResource(R.string.event_details_organizer),
                    items = listOf(eventDetails.organizer)
                ) { organizer, modifier ->
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        BodyMediumText(
                            modifier = Modifier.weight(1f),
                            text = organizer,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        CommonIconButton(
                            imageVector = Icons.Outlined.Email,
                            onClick = { context.startActivity(getMailIntent(organizer)) }
                        )
                    }
                }
            }

            if (eventDetails.attendees.isNotEmpty()) {
                item {
                    SectionCard(
                        sectionIcon = Icons.Outlined.Person,
                        sectionTitle = pluralStringResource(
                            R.plurals.event_details_guests,
                            eventDetails.attendees.size,
                            eventDetails.attendees.size
                        ),
                        items = eventDetails.attendees
                    ) { attendee, modifier ->
                        AttendeeItem(
                            modifier = modifier.clickable { onAttendeePressed(attendee) },
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

private fun getMapsIntent(location: String): Intent =
    Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("http://maps.google.co.in/maps?q=$location")
    }

private fun getMailIntent(email: String): Intent =
    Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    }

@SmallPhonePreview
@Composable
private fun ViewModePreview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        ViewModeContent(
            eventDetails = EVENT_DETAILS,
            onBackPressed = {},
            onEditPressed = {},
            onDeletePressed = {},
            onAttendeePressed = {},
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
            onDeletePressed = {},
            onAttendeePressed = {},
        )
    }
}

@MediumPhonePreview
@Composable
private fun ViewModePreview3() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        ViewModeContent(
            eventDetails = EVENT_DETAILS_GENERIC_DESCRIPTION.copy(eventColor = null),
            onBackPressed = {},
            onEditPressed = {},
            onDeletePressed = {},
            onAttendeePressed = {},
        )
    }
}

@BigPhonePreview
@Composable
private fun ViewModePreview4() {
    CalendarAppTheme(styleType = StyleType.SUMMER, darkTheme = true) {
        ViewModeContent(
            eventDetails = EVENT_DETAILS_EMPTY_DESCRIPTION.copy(
                eventColor = Color.Yellow.toArgb(),
                canModify = false
            ),
            onBackPressed = {},
            onEditPressed = {},
            onDeletePressed = {},
            onAttendeePressed = {},
        )
    }
}
