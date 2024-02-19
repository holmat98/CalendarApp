package com.mateuszholik.uicomponents.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.buttons.CommonIconButton
import com.mateuszholik.uicomponents.calendar.CalendarItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {

    OutlinedCard(
        modifier = modifier,
        onClick = onClick,
    ) {

        Row(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(modifier = Modifier.weight(1f)) {
                content()
            }
            CommonIconButton(
                imageVector = Icons.Default.KeyboardArrowRight,
                onClick = onClick
            )
        }
    }
}

@SmallPhonePreview
@Composable
private fun Preview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface {
            SelectionCard(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
                    .fillMaxWidth(),
                onClick = {}
            ) {
                CalendarItem(
                    email = "email@test.com",
                    name = "email",
                    calendarColor = null
                )
            }
        }
    }
}
