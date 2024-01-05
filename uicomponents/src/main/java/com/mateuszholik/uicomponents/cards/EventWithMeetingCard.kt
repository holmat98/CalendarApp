package com.mateuszholik.uicomponents.cards

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.buttons.CommonButtonDefaults
import com.mateuszholik.uicomponents.buttons.CommonOutlinedButton
import com.mateuszholik.uicomponents.date.EventDate
import com.mateuszholik.uicomponents.text.TitleLargeText
import com.mateuszholik.uicomponents.text.TitleMediumText
import java.time.LocalDateTime

@Composable
fun EventWithMeetingCard(
    title: String,
    description: String,
    eventStart: LocalDateTime,
    eventEnd: LocalDateTime,
    allDay: Boolean,
    joinMeetingButtonText: String,
    onJoinMeetingButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        TitleLargeText(
            modifier = Modifier.padding(
                top = MaterialTheme.spacing.small,
                start = MaterialTheme.spacing.small,
                end = MaterialTheme.spacing.small,
            ),
            text = title
        )
        if (description.isNotEmpty()) {
            TitleMediumText(
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.small,
                    start = MaterialTheme.spacing.small,
                    end = MaterialTheme.spacing.small,
                ),
                text = description
            )
        }
        EventDate(
            modifier = Modifier.padding(MaterialTheme.spacing.small),
            startDate = eventStart, endDate = eventEnd, allDay = allDay
        )
        CommonOutlinedButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(
                    end = MaterialTheme.spacing.small,
                    bottom = MaterialTheme.spacing.small,
                ),
            icon = Icons.Filled.Call,
            text = joinMeetingButtonText,
            onClick = onJoinMeetingButtonClick,
            colors = CommonButtonDefaults.commonOutlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )
    }
}


@SmallPhonePreview
@Composable
private fun SmallPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            EventWithMeetingCard(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                title = "Main Event",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec mollis erat in libero posuere mattis. Aenean dapibus risus consequat, faucibus est at, vestibulum ipsum. Phasellus a elit id nisl euismod rhoncus. Aenean accumsan eget ante et dignissim. Proin non purus vel lacus facilisis facilisis.",
                allDay = false,
                eventStart = LocalDateTime.of(2023, 12, 31, 12, 0, 0),
                eventEnd = LocalDateTime.of(2023, 12, 31, 13, 30, 0),
                joinMeetingButtonText = "Join in Google Meets",
                onJoinMeetingButtonClick = { }
            )
        }
    }
}

@MediumPhonePreview
@Composable
private fun MediumPhonePreview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            EventWithMeetingCard(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                title = "Main Event",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec mollis erat in libero posuere mattis. Aenean dapibus risus consequat, faucibus est at, vestibulum ipsum. Phasellus a elit id nisl euismod rhoncus. Aenean accumsan eget ante et dignissim. Proin non purus vel lacus facilisis facilisis.",
                allDay = false,
                eventStart = LocalDateTime.of(2023, 12, 31, 12, 0, 0),
                eventEnd = LocalDateTime.of(2023, 12, 31, 13, 30, 0),
                joinMeetingButtonText = "Join in Google Meets",
                onJoinMeetingButtonClick = { }
            )
        }
    }
}

@BigPhonePreview
@Composable
private fun BigPhonePreview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            EventWithMeetingCard(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                title = "Main Event",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec mollis erat in libero posuere mattis. Aenean dapibus risus consequat, faucibus est at, vestibulum ipsum. Phasellus a elit id nisl euismod rhoncus. Aenean accumsan eget ante et dignissim. Proin non purus vel lacus facilisis facilisis.",
                allDay = false,
                eventStart = LocalDateTime.of(2023, 12, 31, 12, 0, 0),
                eventEnd = LocalDateTime.of(2023, 12, 31, 13, 30, 0),
                joinMeetingButtonText = "Join in Google Meets",
                onJoinMeetingButtonClick = { }
            )
        }
    }
}

@BigPhonePreview
@Composable
private fun BigPhonePreviewDark() {
    CalendarAppTheme(styleType = StyleType.SUMMER, darkTheme = true) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            EventWithMeetingCard(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                title = "Main Event",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec mollis erat in libero posuere mattis. Aenean dapibus risus consequat, faucibus est at, vestibulum ipsum. Phasellus a elit id nisl euismod rhoncus. Aenean accumsan eget ante et dignissim. Proin non purus vel lacus facilisis facilisis.",
                allDay = false,
                eventStart = LocalDateTime.of(2023, 12, 31, 12, 0, 0),
                eventEnd = LocalDateTime.of(2023, 12, 31, 13, 30, 0),
                joinMeetingButtonText = "Join in Google Meets",
                onJoinMeetingButtonClick = { }
            )
        }
    }
}
