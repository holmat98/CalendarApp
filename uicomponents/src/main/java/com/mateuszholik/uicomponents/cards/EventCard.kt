package com.mateuszholik.uicomponents.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.R
import com.mateuszholik.uicomponents.date.EventDate
import com.mateuszholik.uicomponents.text.TitleLargeText
import com.mateuszholik.uicomponents.text.TitleMediumText
import java.time.LocalDateTime

@Composable
fun EventCard(
    title: String,
    description: String,
    eventStart: LocalDateTime,
    eventEnd: LocalDateTime,
    allDay: Boolean,
    modifier: Modifier = Modifier,
    eventColor: Color? = null,
) {
    Card(modifier = modifier) {
        if (description.isEmpty()) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f),
                painter = painterResource(id = R.drawable.event_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        }

        Row(
            modifier = Modifier.padding(
                top = MaterialTheme.spacing.small,
                start = MaterialTheme.spacing.small,
                end = MaterialTheme.spacing.small,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            eventColor?.let {
                Box(
                    modifier = Modifier
                        .padding(end = MaterialTheme.spacing.small)
                        .size(MaterialTheme.sizing.small)
                        .background(
                            color = it,
                            shape = RoundedCornerShape(8.dp)
                        )
                )
            }
            TitleLargeText(text = title)
        }

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
            EventCard(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                title = "Main Event",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec mollis erat in libero posuere mattis. Aenean dapibus risus consequat, faucibus est at, vestibulum ipsum. Phasellus a elit id nisl euismod rhoncus. Aenean accumsan eget ante et dignissim. Proin non purus vel lacus facilisis facilisis.",
                allDay = false,
                eventStart = LocalDateTime.of(2023, 12, 31, 12, 0, 0),
                eventEnd = LocalDateTime.of(2023, 12, 31, 13, 30, 0),
                eventColor = Color.Magenta,
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
            EventCard(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                title = "Main Event",
                description = "",
                allDay = false,
                eventStart = LocalDateTime.of(2023, 12, 31, 12, 0, 0),
                eventEnd = LocalDateTime.of(2023, 12, 31, 13, 30, 0),
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
            EventCard(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                title = "Main Event",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec mollis erat in libero posuere mattis. Aenean dapibus risus consequat, faucibus est at, vestibulum ipsum. Phasellus a elit id nisl euismod rhoncus. Aenean accumsan eget ante et dignissim. Proin non purus vel lacus facilisis facilisis.",
                allDay = false,
                eventStart = LocalDateTime.of(2023, 12, 31, 12, 0, 0),
                eventEnd = LocalDateTime.of(2023, 12, 31, 13, 30, 0),
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
            EventCard(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                title = "Main Event",
                description = "",
                allDay = false,
                eventStart = LocalDateTime.of(2023, 12, 31, 12, 0, 0),
                eventEnd = LocalDateTime.of(2023, 12, 31, 13, 30, 0),
            )
        }
    }
}
