package com.mateuszholik.uicomponents.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.text.BodySmallText
import com.mateuszholik.uicomponents.text.TitleMediumText

@Composable
fun CommonOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    colors: CommonButtonColors = CommonButtonDefaults.commonOutlinedButtonColors(),
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = colors.contentColor,
            containerColor = colors.containerColor,
        )
    ) {
        icon?.let {
            Icon(
                modifier = Modifier.padding(
                    top = 4.dp,
                    end = 8.dp
                ),
                imageVector = it,
                contentDescription = null
            )
        }
        BodySmallText(
            text = text,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun SpringPreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        CommonOutlinedButton(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal),
            text = "Click",
            icon = Icons.Default.Add,
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun SpringPreviewDark() {
    CalendarAppTheme(styleType = StyleType.SPRING, darkTheme = true) {
        CommonOutlinedButton(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal),
            text = "Click",
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun SummerPreview() {
    CalendarAppTheme(styleType = StyleType.SUMMER) {
        CommonOutlinedButton(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal),
            text = "Click",
            icon = Icons.Default.FavoriteBorder,
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun SummerPreviewDark() {
    CalendarAppTheme(styleType = StyleType.SUMMER, darkTheme = true) {
        CommonOutlinedButton(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal),
            text = "Click",
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun AutumnPreview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        CommonOutlinedButton(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal),
            text = "Click",
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun AutumnPreviewDark() {
    CalendarAppTheme(styleType = StyleType.AUTUMN, darkTheme = true) {
        CommonOutlinedButton(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal),
            text = "Click",
            icon = Icons.Default.Check,
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun WinterPreview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        CommonOutlinedButton(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal),
            text = "Click",
            icon = Icons.Default.Add,
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun WinterPreviewDark() {
    CalendarAppTheme(styleType = StyleType.WINTER, darkTheme = true) {
        CommonOutlinedButton(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal),
            text = "Click",
            onClick = { }
        )
    }
}
