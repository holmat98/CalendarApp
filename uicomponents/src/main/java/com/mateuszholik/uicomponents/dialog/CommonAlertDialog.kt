package com.mateuszholik.uicomponents.dialog

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.mateuszholik.uicomponents.buttons.CommonTextButton
import com.mateuszholik.uicomponents.text.BodySmallText
import com.mateuszholik.uicomponents.text.TitleLargeText

@Composable
fun CommonAlertDialog(
    @StringRes titleResId: Int,
    @StringRes messageResId: Int,
    @StringRes negativeButtonResId: Int,
    @StringRes positiveButtonResId: Int,
    onNegativeButtonClicked: () -> Unit,
    onPositiveButtonClicked: () -> Unit,
    icon: ImageVector? = null,
) {
    AlertDialog(
        title = { TitleLargeText(textResId = titleResId) },
        text = { BodySmallText(textResId = messageResId) },
        confirmButton = {
            CommonTextButton(
                textResId = positiveButtonResId,
                onClick = onPositiveButtonClicked
            )
        },
        dismissButton = {
            CommonTextButton(
                textResId = negativeButtonResId,
                onClick = onNegativeButtonClicked
            )
        },
        icon = if (icon != null) {
            { Icon(imageVector = icon, contentDescription = null) }
        } else {
            null
        },
        onDismissRequest = {}
    )
}
