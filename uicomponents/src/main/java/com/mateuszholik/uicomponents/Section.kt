package com.mateuszholik.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.text.TitleMediumText

@Composable
fun <T> Section(
    sectionIcon: ImageVector,
    sectionTitle: String,
    items: List<T>,
    modifier: Modifier = Modifier,
    sectionItemContent: @Composable (T) -> Unit,
) {
    Row(modifier = modifier) {
        Icon(
            modifier = Modifier
                .padding(
                    top = 3.dp,
                    end = MaterialTheme.spacing.normal
                )
                .size(MaterialTheme.sizing.small),
            imageVector = sectionIcon,
            contentDescription = null,
        )

        Column(modifier = Modifier.weight(1f)) {
            TitleMediumText(text = sectionTitle)
            items.forEach { sectionItemContent(it) }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            Section(
                sectionIcon = Icons.Default.AccountCircle,
                sectionTitle = "Title",
                items = listOf("Item 1", "Item 2", "Item 3", "Item 4"),
            ) { TitleMediumText(it) }
        }
    }
}
