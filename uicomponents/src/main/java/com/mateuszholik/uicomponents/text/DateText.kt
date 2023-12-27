package com.mateuszholik.uicomponents.text

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.uicomponents.extensions.capitalized
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DateText(
    date: YearMonth,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onSecondary,
) {
    val monthName = date.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())

    Row(modifier = modifier) {
        HeadlineSmallText(
            text = "$monthName ${date.year}".capitalized(),
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
