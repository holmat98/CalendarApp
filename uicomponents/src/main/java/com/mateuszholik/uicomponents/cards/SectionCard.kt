package com.mateuszholik.uicomponents.cards

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.cornerRadius
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.buttons.CommonIconButton
import com.mateuszholik.uicomponents.buttons.CommonIconButtonDefaults
import com.mateuszholik.uicomponents.text.TitleMediumText

@Composable
fun <T> SectionCard(
    sectionIcon: ImageVector,
    sectionTitle: String,
    items: List<T>,
    modifier: Modifier = Modifier,
    areItemsHidden: Boolean = true,
    cardColors: SectionCardColors = SectionCardDefaults.colors(),
    sectionItemContent: @Composable (item: T, modifier: Modifier) -> Unit,
) {
    var areItemsVisible by remember { mutableStateOf(!areItemsHidden) }
    var currentRotation by remember {
        mutableFloatStateOf(
            if (areItemsHidden) {
                0.0f
            } else {
                -90.0f
            }
        )
    }
    val rotation = remember { Animatable(currentRotation) }

    LaunchedEffect(areItemsVisible) {
        val targetRotation = if (areItemsVisible) {
            -90.0f
        } else {
            0.0f
        }

        rotation.animateTo(targetRotation) {
            currentRotation = value
        }
    }

    Card(
        modifier = modifier.animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = cardColors.cardContainerColor,
            contentColor = cardColors.cardContentColor,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = cardColors.headerContainerColor,
                    shape = RoundedCornerShape(
                        bottomStart = MaterialTheme.cornerRadius.cardRadius,
                        bottomEnd = MaterialTheme.cornerRadius.cardRadius,
                    ),
                )
                .zIndex(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = MaterialTheme.spacing.small),
                imageVector = sectionIcon,
                contentDescription = null,
                tint = cardColors.headerContentColor,
            )
            TitleMediumText(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.spacing.small)
                    .weight(1f),
                text = sectionTitle,
                textAlign = TextAlign.Center,
                color = cardColors.headerContentColor
            )
            CommonIconButton(
                modifier = Modifier
                    .padding(end = MaterialTheme.spacing.small)
                    .rotate(currentRotation),
                imageVector = Icons.Default.KeyboardArrowLeft,
                onClick = { areItemsVisible = !areItemsVisible },
                colors = CommonIconButtonDefaults.colors(contentColor = cardColors.headerContentColor)
            )
        }
        AnimatedVisibility(
            visible = areItemsVisible,
            enter = slideInVertically() + expandVertically() + fadeIn(),
            exit = slideOutVertically() + shrinkVertically() + fadeOut(),
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.spacing.small,
                        start = MaterialTheme.spacing.small,
                        end = MaterialTheme.spacing.small,
                    )
                    .fillMaxWidth(),
            ) {
                items.forEach { item ->
                    sectionItemContent(
                        item,
                        Modifier.padding(bottom = MaterialTheme.spacing.small),
                    )
                }
            }
        }
    }
}

@Immutable
data class SectionCardColors internal constructor(
    val headerContainerColor: Color,
    val headerContentColor: Color,
    val cardContainerColor: Color,
    val cardContentColor: Color,
)

object SectionCardDefaults {

    @Composable
    @ReadOnlyComposable
    fun colors(
        headerContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
        headerContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        cardContainerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        cardContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    ): SectionCardColors =
        SectionCardColors(
            headerContainerColor = headerContainerColor,
            headerContentColor = headerContentColor,
            cardContainerColor = cardContainerColor,
            cardContentColor = cardContentColor,
        )
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
            SectionCard(
                modifier = Modifier.padding(MaterialTheme.spacing.normal),
                sectionIcon = Icons.Default.AccountCircle,
                sectionTitle = "Title",
                items = listOf("Item 1", "Item 2", "Item 3", "Item 4"),
            ) { item, modifier -> TitleMediumText(item, modifier) }
        }
    }
}

@Preview
@Composable
private fun Preview2() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            SectionCard(
                modifier = Modifier.padding(MaterialTheme.spacing.normal),
                sectionIcon = Icons.Default.AccountCircle,
                sectionTitle = "Title",
                items = listOf("Item 1", "Item 2", "Item 3", "Item 4"),
                areItemsHidden = false,
            ) { item, modifier -> TitleMediumText(item, modifier) }
        }
    }
}
