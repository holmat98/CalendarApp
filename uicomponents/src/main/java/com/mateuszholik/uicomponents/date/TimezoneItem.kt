package com.mateuszholik.uicomponents.date

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.mateuszholik.dateutils.extensions.capitalized
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.text.LabelLargeText
import com.mateuszholik.uicomponents.text.LabelMediumText
import com.mateuszholik.uicomponents.text.LabelSmallText
import java.util.TimeZone

@Composable
fun TimezoneItem(
    timezone: TimeZone,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        LabelLargeText(
            modifier = Modifier.fillMaxWidth(),
            text = timezone.displayName.capitalized(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        LabelMediumText(text = timezone.id)
        LabelSmallText(text = timezone.getDisplayName(false, TimeZone.SHORT))
    }
}

@SmallPhonePreview
@Composable
private fun Preview() {
    CalendarAppTheme(styleType = StyleType.SUMMER) {
        Surface {
            TimezoneItem(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                timezone = TimeZone.getDefault()
            )
        }
    }
}
