package com.mateuszholik.uicomponents.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.text.LabelSmallText

@Composable
fun CommonSmallButton(
    @StringRes textResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: CommonButtonColors = CommonButtonDefaults.commonButtonColors(),
    isEnabled: Boolean = true,
) {
    CommonSmallButton(
        text = stringResource(textResId),
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        isEnabled = isEnabled,
    )
}

@Composable
fun CommonSmallButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: CommonButtonColors = CommonButtonDefaults.commonButtonColors(),
    isEnabled: Boolean = true,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = colors.contentColor,
            containerColor = colors.containerColor,
        ),
        enabled = isEnabled,
    ) {
        LabelSmallText(text = text)
    }
}

@Preview
@Composable
private fun SpringPreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        CommonButton(
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
        CommonButton(
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
        CommonButton(
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
        CommonButton(
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
        CommonButton(
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
        CommonButton(
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
        CommonButton(
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
        CommonButton(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.normal)
                .fillMaxWidth(),
            text = "Click",
            onClick = { }
        )
    }
}
