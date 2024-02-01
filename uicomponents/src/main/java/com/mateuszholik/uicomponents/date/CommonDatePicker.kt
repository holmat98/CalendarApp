package com.mateuszholik.uicomponents.date

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mateuszholik.dateutils.Milliseconds
import com.mateuszholik.dateutils.Seconds
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.buttons.CommonTextButton
import com.mateuszholik.dateutils.extensions.asDayString
import com.mateuszholik.uicomponents.text.BodySmallText
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonDatePicker(
    date: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    var shouldShowPicker by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = modifier,
        onClick = { shouldShowPicker = true }
    ) {
        BodySmallText(
            modifier = Modifier.padding(MaterialTheme.spacing.normal),
            text = date.asDayString()
        )
    }

    if (shouldShowPicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = Milliseconds.ofSeconds(Seconds.ofDateTime(date.atStartOfDay())).value
        )
        DatePickerDialog(
            onDismissRequest = { shouldShowPicker = false },
            confirmButton = {
                CommonTextButton(
                    textResId = android.R.string.ok,
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val localDate = Milliseconds.ofMillis(it).asDate()
                            onDateSelected(localDate)
                        }
                        shouldShowPicker = false
                    }
                )
            },
            dismissButton = {
                CommonTextButton(
                    textResId = android.R.string.cancel,
                    onClick = { shouldShowPicker = false }
                )
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@SmallPhonePreview
@Composable
private fun Preview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        Surface {
            CommonDatePicker(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                date = LocalDate.of(2024, 1, 27),
                onDateSelected = {}
            )
        }
    }
}
