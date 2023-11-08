package com.mateuszholik.uicomponents.text

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.textSizing
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DateText(
    date: LocalDate,
    textSize: TextUnit,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onSecondary,
) {
    val locale = Locale.getDefault()
    val monthName = date.month.getDisplayName(TextStyle.FULL_STANDALONE, locale)

    Row(modifier = modifier) {
        when (locale.language) {
            Locale.ENGLISH.language -> {
                Text(
                    text = "$monthName' ",
                    fontSize = textSize,
                    color = textColor
                )
                Text(
                    text = "${date.dayOfMonth} ",
                    fontSize = textSize,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            }
            else -> {
                Text(
                    text = "${date.dayOfMonth} ",
                    fontSize = textSize,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$monthName ",
                    fontSize = textSize,
                    color = textColor
                )
            }
        }

        Text(
            text = date.year.toString(),
            fontSize = textSize,
            color = textColor
        )
    }

}

@SmallPhonePreview
@Composable
private fun Preview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        DateText(
            date = LocalDate.of(2023, 11, 5),
            textSize = MaterialTheme.textSizing.header
        )
    }
}
