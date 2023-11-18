package com.mateuszholik.uicomponents.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.mateuszholik.designsystem.cornerRadius
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.extensions.shimmerEffect

@Composable
fun CalendarShimmerView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = MaterialTheme.spacing.normal)
                .fillMaxWidth()
                .height(MaterialTheme.sizing.normal)
                .clip(RoundedCornerShape(MaterialTheme.cornerRadius.small))
                .shimmerEffect()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(7) {
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.sizing.normal)
                        .clip(CircleShape)
                        .shimmerEffect()
                )
            }
        }

        Divider(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.small),
            color = MaterialTheme.colorScheme.secondaryContainer
        )

        repeat(4) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.small),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(7) {
                    Box(
                        modifier = Modifier
                            .size(MaterialTheme.sizing.normal)
                            .clip(CircleShape)
                            .shimmerEffect()
                    )
                }
            }
        }
    }
}
