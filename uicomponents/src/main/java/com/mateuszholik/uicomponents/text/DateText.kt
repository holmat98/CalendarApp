package com.mateuszholik.uicomponents.text

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.dateutils.extensions.toText
import java.time.YearMonth

@Composable
fun DateText(
    date: YearMonth,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onSecondary,
) {
    Row(modifier = modifier) {
        HeadlineSmallText(
            text = date.toText(),
            color = textColor
        )
    }

}

@SmallPhonePreview
@Composable
private fun Preview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        DateText(
            date = YearMonth.of(2023, 11),
        )
    }
}
