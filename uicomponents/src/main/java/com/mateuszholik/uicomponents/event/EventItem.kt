package com.mateuszholik.uicomponents.event

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.cornerRadius
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.extensions.asTimeString
import com.mateuszholik.uicomponents.text.HeadlineSmallText
import com.mateuszholik.uicomponents.text.TitleMediumText
import java.time.LocalDateTime

@Composable
fun EventItem(
    title: String,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    onEventClicked: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    Column(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(MaterialTheme.cornerRadius.normal)
            )
            .clickable { onEventClicked() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        HeadlineSmallText(
            modifier = Modifier.padding(
                start = MaterialTheme.spacing.normal,
                top = MaterialTheme.spacing.small,
                bottom = MaterialTheme.spacing.small,
            ),
            text = title, color = contentColor
        )
        TitleMediumText(
            modifier = Modifier.padding(
                start = MaterialTheme.spacing.normal,
                bottom = MaterialTheme.spacing.small
            ),
            text = "${startTime.asTimeString()} - ${endTime.asTimeString()}",
            color = contentColor
        )

    }
}

@SmallPhonePreview
@Composable
private fun SmallPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        EventItem(
            modifier = Modifier.fillMaxWidth(),
            title = "Event 1",
            startTime = LocalDateTime.of(2023, 11, 12, 13, 0),
            endTime = LocalDateTime.of(2023, 11, 12, 14, 0),
            onEventClicked = { }
        )
    }
}

@MediumPhonePreview
@Composable
private fun MediumPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        EventItem(
            modifier = Modifier.fillMaxWidth(),
            title = "Event 2",
            startTime = LocalDateTime.of(2023, 11, 12, 13, 0),
            endTime = LocalDateTime.of(2023, 11, 12, 14, 0),
            onEventClicked = { }
        )
    }
}

@BigPhonePreview
@Composable
private fun BigPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        EventItem(
            modifier = Modifier.fillMaxWidth(),
            title = "Event 3",
            startTime = LocalDateTime.of(2023, 11, 12, 13, 0),
            endTime = LocalDateTime.of(2023, 11, 12, 14, 0),
            onEventClicked = { }
        )
    }
}
