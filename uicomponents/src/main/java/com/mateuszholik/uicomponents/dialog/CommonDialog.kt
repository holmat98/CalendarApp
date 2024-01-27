package com.mateuszholik.uicomponents.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.mateuszholik.designsystem.cornerRadius
import com.mateuszholik.designsystem.spacing

@Composable
fun CommonDialog(
    onDismissRequest: () -> Unit,
    content: LazyListScope.() -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(shape = RoundedCornerShape(MaterialTheme.cornerRadius.normal)) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.normal),
                contentPadding = PaddingValues(MaterialTheme.spacing.normal),
                content = content
            )
        }
    }
}
