package com.mateuszholik.uicomponents.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.text.LabelLargeText

@Composable
fun CommonTextButton(
    @StringRes textResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: CommonButtonColors = CommonButtonDefaults.commonTextButtonColors(),
) {
    CommonTextButton(
        text = stringResource(textResId),
        onClick = onClick,
        modifier = modifier,
        colors = colors
    )
}

@Composable
fun CommonTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: CommonButtonColors = CommonButtonDefaults.commonTextButtonColors(),
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = colors.containerColor,
            contentColor = colors.contentColor,
        )
    ) {
        LabelLargeText(text = text)
    }
}

@Preview
@Composable
private fun SpringPreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        CommonTextButton(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.normal)
                .fillMaxWidth(),
            text = "Click",
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun SpringPreviewDark() {
    CalendarAppTheme(styleType = StyleType.SPRING, darkTheme = true) {
        CommonTextButton(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.normal)
                .fillMaxWidth(),
            text = "Click",
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun SummerPreview() {
    CalendarAppTheme(styleType = StyleType.SUMMER) {
        CommonTextButton(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.normal)
                .fillMaxWidth(),
            text = "Click",
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun SummerPreviewDark() {
    CalendarAppTheme(styleType = StyleType.SUMMER, darkTheme = true) {
        CommonTextButton(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.normal)
                .fillMaxWidth(),
            text = "Click",
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun AutumnPreview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        CommonTextButton(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.normal)
                .fillMaxWidth(),
            text = "Click",
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun AutumnPreviewDark() {
    CalendarAppTheme(styleType = StyleType.AUTUMN, darkTheme = true) {
        CommonTextButton(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.normal)
                .fillMaxWidth(),
            text = "Click",
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun WinterPreview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        CommonTextButton(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.normal)
                .fillMaxWidth(),
            text = "Click",
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun WinterPreviewDark() {
    CalendarAppTheme(styleType = StyleType.WINTER, darkTheme = true) {
        CommonTextButton(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.normal)
                .fillMaxWidth(),
            text = "Click",
            onClick = { }
        )
    }
}
