package com.mateuszholik.uicomponents.checkbox

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.text.TitleSmallText

@Composable
fun CommonCheckbox(
    isChecked: Boolean,
    text: String,
    onChecked: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: CommonCheckboxColors = CommonCheckboxDefaults.colors(),
) {
    Row(
        modifier = modifier.clickable { onChecked(!isChecked) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Checkbox(
            modifier = Modifier.padding(end = MaterialTheme.spacing.small),
            checked = isChecked,
            onCheckedChange = onChecked,
            colors = CheckboxDefaults.colors(
                checkedColor = colors.checkedColor,
                uncheckedColor = colors.uncheckedColor
            )
        )

        TitleSmallText(text = text, color = colors.textColor)
    }
}

@Immutable
data class CommonCheckboxColors internal constructor(
    val textColor: Color,
    val checkedColor: Color,
    val uncheckedColor: Color,
)

object CommonCheckboxDefaults {

    @Composable
    @ReadOnlyComposable
    fun colors(
        textColor: Color = MaterialTheme.colorScheme.onSurface,
        checkedColor: Color = MaterialTheme.colorScheme.primary,
        uncheckedColor: Color = MaterialTheme.colorScheme.outline,
    ): CommonCheckboxColors =
        CommonCheckboxColors(
            textColor = textColor,
            checkedColor = checkedColor,
            uncheckedColor = uncheckedColor
        )
}

@SmallPhonePreview
@Composable
private fun Preview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            CommonCheckbox(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                isChecked = true,
                text = "Checkbox",
                onChecked = {}
            )
        }
    }
}

@SmallPhonePreview
@Composable
private fun Preview2() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            CommonCheckbox(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.normal)
                    .fillMaxWidth(),
                isChecked = false,
                text = "Checkbox",
                onChecked = {}
            )
        }
    }
}
