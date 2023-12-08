package com.mateuszholik.calendarapp.ui.calendarprofiles

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.Modifier
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.CALENDARS
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.uicomponents.checkbox.CommonCheckbox
import com.mateuszholik.uicomponents.text.HeadlineLargeText
import com.mateuszholik.uicomponents.text.TitleLargeText

@Composable
fun CalendarProfileScreen(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Content(
            paddingValues = it,
            calendars = emptyMap(),
            onCalendarChecked = {}
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(
    paddingValues: PaddingValues,
    calendars: Map<String, List<Calendar>>,
    onCalendarChecked: (Pair<Long, Boolean>) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        item {
            HeadlineLargeText(
                textResId = R.string.calendar_profile_header,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        calendars.forEach { (accountName, calendars) ->
            stickyHeader {
                TitleLargeText(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                    text = accountName,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            items(
                items = calendars,
                key = { it.id }
            ) { calendar ->
                CommonCheckbox(
                    modifier = Modifier
                        .padding(top = MaterialTheme.spacing.small)
                        .fillMaxWidth(),
                    isChecked = calendar.isVisible,
                    text = calendar.calendarName,
                    onChecked = { onCalendarChecked(calendar.id to it) }
                )
            }
        }
    }
}

@SmallPhonePreview
@Composable
private fun SmallPhonePreview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            Content(
                paddingValues = PaddingValues(MaterialTheme.spacing.normal),
                calendars = CALENDARS,
                onCalendarChecked = {}
            )
        }
    }
}

@MediumPhonePreview
@Composable
private fun MediumPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            Content(
                paddingValues = PaddingValues(MaterialTheme.spacing.normal),
                calendars = CALENDARS,
                onCalendarChecked = {}
            )
        }
    }
}

@BigPhonePreview
@Composable
private fun BigPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SUMMER) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            Content(
                paddingValues = PaddingValues(MaterialTheme.spacing.normal),
                calendars = CALENDARS,
                onCalendarChecked = {}
            )
        }
    }
}
