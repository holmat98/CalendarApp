package com.mateuszholik.uicomponents.textfield

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.text.LabelSmallText

@Composable
fun TextFieldWithIcon(
    text: String,
    onTextChanged: (String) -> Unit,
    icon: ImageVector,
    hint: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.padding(
                end = MaterialTheme.spacing.small,
                top = MaterialTheme.spacing.tiny,
            ),
            imageVector = icon,
            contentDescription = null
        )
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = text,
            onValueChange = onTextChanged,
            label = { LabelSmallText(text = hint) },
            singleLine = singleLine,
            minLines = minLines,
            maxLines = maxLines,
        )
    }
}

@SmallPhonePreview
@Composable
private fun SummerPreview() {
    CalendarAppTheme(styleType = StyleType.SUMMER) {
        Surface {
            TextFieldWithIcon(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                text = "",
                onTextChanged = {},
                icon = Icons.Default.Call,
                hint = "Provide phone number"
            )
        }
    }
}

@MediumPhonePreview
@Composable
private fun SummerPreviewDark() {
    CalendarAppTheme(styleType = StyleType.SUMMER, darkTheme = true) {
        Surface {
            TextFieldWithIcon(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                text = "123456789",
                onTextChanged = {},
                icon = Icons.Default.Call,
                hint = "Provide phone number"
            )
        }
    }
}
