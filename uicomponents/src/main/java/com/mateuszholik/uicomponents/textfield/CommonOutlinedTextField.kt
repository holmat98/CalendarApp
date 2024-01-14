package com.mateuszholik.uicomponents.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.text.LabelSmallText

@Composable
fun CommonOutlinedTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = onTextChanged,
        label = { LabelSmallText(text = hint) },
        singleLine = singleLine,
        minLines = minLines,
        maxLines = maxLines,
    )
}

@SmallPhonePreview
@Composable
private fun SummerPreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface {
            CommonOutlinedTextField(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                text = "",
                onTextChanged = {},
                hint = "Provide phone number"
            )
        }
    }
}

@MediumPhonePreview
@Composable
private fun SummerPreviewDark() {
    CalendarAppTheme(styleType = StyleType.SPRING, darkTheme = true) {
        Surface {
            CommonOutlinedTextField(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                text = "123456789",
                onTextChanged = {},
                hint = "Provide phone number"
            )
        }
    }
}
