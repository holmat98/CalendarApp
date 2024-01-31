package com.mateuszholik.uicomponents.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.text.LabelLargeText
import com.mateuszholik.uicomponents.text.LabelMediumText

@Composable
fun CalendarItem(
    email: String,
    name: String,
    calendarColor: Int?,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(end = MaterialTheme.spacing.small)
                .size(MaterialTheme.sizing.tiny)
                .background(
                    shape = CircleShape,
                    color = calendarColor?.let { Color(it) } ?: MaterialTheme.colorScheme.surfaceVariant
                ),
        )

        Column {
            LabelLargeText(text = name)
            LabelMediumText(text = email)
        }
    }
}

@SmallPhonePreview
@Composable
private fun Preview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface {
            CalendarItem(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                email = "email@test.com",
                name = "calendar",
                calendarColor = Color.Magenta.toArgb()
            )
        }
    }
}
