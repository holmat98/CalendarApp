package com.mateuszholik.uicomponents.dialog

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.mateuszholik.designsystem.cornerRadius

@Composable
fun CommonDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(shape = RoundedCornerShape(MaterialTheme.cornerRadius.normal)) {
            content()
        }
    }
}
