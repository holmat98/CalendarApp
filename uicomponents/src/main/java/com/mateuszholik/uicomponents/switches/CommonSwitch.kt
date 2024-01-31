package com.mateuszholik.uicomponents.switches

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.text.LabelLargeText

@Composable
fun CommonSwitch(
    text: String,
    isSelected: Boolean,
    onSelectionChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        LabelLargeText(text = text)
        Switch(
            modifier = Modifier.padding(start = MaterialTheme.spacing.small),
            checked = isSelected,
            onCheckedChange = onSelectionChanged
        )
    }
}

@SmallPhonePreview
@Composable
private fun Preview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        Surface {
            CommonSwitch(
                modifier = Modifier.padding(MaterialTheme.spacing.normal).fillMaxWidth(),
                text = "Select",
                isSelected = false,
                onSelectionChanged = {}
            )
        }
    }
}

@SmallPhonePreview
@Composable
private fun Preview2() {
    CalendarAppTheme(styleType = StyleType.WINTER, darkTheme = true) {
        Surface {
            CommonSwitch(
                modifier = Modifier.padding(MaterialTheme.spacing.normal).fillMaxWidth(),
                text = "Select",
                isSelected = true,
                onSelectionChanged = {}
            )
        }
    }
}
