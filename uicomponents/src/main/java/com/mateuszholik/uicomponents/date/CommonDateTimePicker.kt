package com.mateuszholik.uicomponents.date

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mateuszholik.dateutils.Milliseconds
import com.mateuszholik.dateutils.Seconds
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.R
import com.mateuszholik.uicomponents.buttons.CommonTextButton
import com.mateuszholik.dateutils.extensions.asDayString
import com.mateuszholik.dateutils.extensions.asTimeString
import com.mateuszholik.dateutils.extensions.copy
import com.mateuszholik.uicomponents.text.BodySmallText
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonDateTimePicker(
    date: LocalDateTime,
    onDateSelected: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier,
) {
    var shouldShowDatePicker by remember { mutableStateOf(false) }
    var shouldShowTimePicker by remember { mutableStateOf(false) }

    OutlinedCard(modifier = modifier) {
        Row(
            modifier = Modifier.padding(MaterialTheme.spacing.normal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BodySmallText(
                modifier = Modifier
                    .weight(1f)
                    .clickable { shouldShowDatePicker = true },
                text = date.asDayString()
            )
            Spacer(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.small)
                    .width(1.dp)
                    .height(MaterialTheme.sizing.tiny)
                    .background(color = LocalContentColor.current)
            )
            BodySmallText(
                modifier = Modifier.clickable { shouldShowTimePicker = true },
                text = date.asTimeString()
            )
        }
    }

    if (shouldShowDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = Milliseconds.ofSeconds(Seconds.ofDateTime(date)).value
        )
        DatePickerDialog(
            onDismissRequest = { shouldShowDatePicker = false },
            confirmButton = {
                CommonTextButton(
                    textResId = android.R.string.ok,
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val localDate = Milliseconds.ofMillis(it).asDate()
                            onDateSelected(
                                date.copy(
                                    year = localDate.year,
                                    month = localDate.monthValue,
                                    dayOfMonth = localDate.dayOfMonth,
                                )
                            )
                        }
                        shouldShowDatePicker = false
                    }
                )
            },
            dismissButton = {
                CommonTextButton(
                    textResId = android.R.string.cancel,
                    onClick = { shouldShowDatePicker = false }
                )
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (shouldShowTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = date.hour,
            initialMinute = date.minute,
            is24Hour = true,
        )
        AlertDialog(
            title = { BodySmallText(textResId = R.string.select_time) },
            text = {
                TimePicker(state = timePickerState)
            },
            onDismissRequest = { shouldShowTimePicker = false },
            confirmButton = {
                CommonTextButton(
                    textResId = android.R.string.ok,
                    onClick = {
                        onDateSelected(
                            date.copy(
                                hour = timePickerState.hour,
                                minute = timePickerState.minute
                            )
                        )
                        shouldShowTimePicker = false
                    }
                )
            },
            dismissButton = {
                CommonTextButton(
                    textResId = android.R.string.cancel,
                    onClick = { shouldShowTimePicker = false }
                )
            }
        )
    }
}

@SmallPhonePreview
@Composable
private fun Preview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        Surface {
            CommonDateTimePicker(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                date = LocalDateTime.of(2024, 1, 27, 12, 0, 0),
                onDateSelected = {}
            )
        }
    }
}
