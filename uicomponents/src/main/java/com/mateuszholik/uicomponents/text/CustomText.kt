package com.mateuszholik.uicomponents.text

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview

@Composable
fun DisplayLargeText(
    @StringRes textResId: Int,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    DisplayLargeText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun DisplayLargeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.displayLarge,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun DisplayMediumText(
    @StringRes textResId: Int,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    DisplayMediumText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun DisplayMediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.displayMedium,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun DisplaySmallText(
    @StringRes textResId: Int,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    DisplaySmallText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun DisplaySmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.displaySmall,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun HeadlineLargeText(
    @StringRes textResId: Int,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    HeadlineLargeText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun HeadlineLargeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun HeadlineMediumText(
    @StringRes textResId: Int,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    HeadlineMediumText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun HeadlineMediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun HeadlineSmallText(
    @StringRes textResId: Int,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    HeadlineSmallText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun HeadlineSmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun TitleLargeText(
    @StringRes textResId: Int,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    TitleLargeText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun TitleLargeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun TitleMediumText(
    @StringRes textResId: Int,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    TitleMediumText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun TitleMediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun TitleSmallText(
    @StringRes textResId: Int,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    TitleSmallText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun TitleSmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun BodyLargeText(
    @StringRes textResId: Int,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    BodyLargeText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun BodyLargeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun BodyMediumText(
    @StringRes textResId: Int,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    BodyLargeText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun BodyMediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun BodySmallText(
    @StringRes textResId: Int,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    BodySmallText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun BodySmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun LabelLargeText(
    @StringRes textResId: Int,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    LabelLargeText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun LabelLargeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun LabelMediumText(
    @StringRes textResId: Int,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    LabelMediumText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun LabelMediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun LabelSmallText(
    @StringRes textResId: Int,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    LabelSmallText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@Composable
fun LabelSmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
    )
}

@SmallPhonePreview
@Composable
private fun TextPreview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        Surface(color = MaterialTheme.colorScheme.primary) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                DisplayLargeText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                DisplayMediumText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                DisplaySmallText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                HeadlineLargeText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                HeadlineMediumText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                HeadlineSmallText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                TitleLargeText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                TitleMediumText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                TitleSmallText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                BodyLargeText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                BodyMediumText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                BodySmallText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                LabelLargeText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                LabelMediumText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                LabelSmallText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@MediumPhonePreview
@Composable
private fun TextPreview2() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                DisplayLargeText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                DisplayMediumText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                DisplaySmallText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                HeadlineLargeText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                HeadlineMediumText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                HeadlineSmallText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                TitleLargeText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                TitleMediumText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                TitleSmallText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                BodyLargeText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                BodyMediumText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                BodySmallText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                LabelLargeText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                LabelMediumText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                LabelSmallText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@BigPhonePreview
@Composable
private fun TextPreview3() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                DisplayLargeText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                DisplayMediumText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                DisplaySmallText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                HeadlineLargeText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                HeadlineMediumText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                HeadlineSmallText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                TitleLargeText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                TitleMediumText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                TitleSmallText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                BodyLargeText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                BodyMediumText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                BodySmallText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                LabelLargeText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                LabelMediumText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
                LabelSmallText(text = "Hello November", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}
