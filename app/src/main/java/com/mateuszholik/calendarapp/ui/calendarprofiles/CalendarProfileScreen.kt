package com.mateuszholik.calendarapp.ui.calendarprofiles

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mateuszholik.calendarapp.R
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.text.HeadlineLargeText

@Composable
fun CalendarProfileScreen(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Content(paddingValues = it)
    }
}

@Composable
private fun Content(paddingValues: PaddingValues) {
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
    }
}

@SmallPhonePreview
@Composable
private fun SmallPhonePreview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            Content(paddingValues = PaddingValues(MaterialTheme.spacing.normal))
        }
    }
}

@MediumPhonePreview
@Composable
private fun MediumPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            Content(paddingValues = PaddingValues(MaterialTheme.spacing.normal))
        }
    }
}

@BigPhonePreview
@Composable
private fun BigPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SUMMER) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            Content(paddingValues = PaddingValues(MaterialTheme.spacing.normal))
        }
    }
}
