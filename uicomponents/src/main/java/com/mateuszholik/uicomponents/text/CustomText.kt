package com.mateuszholik.uicomponents.text

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview

@Composable
fun DisplayLargeText(
    @StringRes textResId: Int,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    DisplayLargeText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun DisplayLargeText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.displayLarge,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun DisplayMediumText(
    @StringRes textResId: Int,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    DisplayMediumText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun DisplayMediumText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.displayMedium,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun DisplaySmallText(
    @StringRes textResId: Int,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    DisplaySmallText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun DisplaySmallText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.displaySmall,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun HeadlineLargeText(
    @StringRes textResId: Int,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    HeadlineLargeText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun HeadlineLargeText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun HeadlineMediumText(
    @StringRes textResId: Int,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    HeadlineMediumText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun HeadlineMediumText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun HeadlineSmallText(
    @StringRes textResId: Int,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    HeadlineSmallText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun HeadlineSmallText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun TitleLargeText(
    @StringRes textResId: Int,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    TitleLargeText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun TitleLargeText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun TitleMediumText(
    @StringRes textResId: Int,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    TitleMediumText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun TitleMediumText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun TitleSmallText(
    @StringRes textResId: Int,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    TitleSmallText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun TitleSmallText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun BodyLargeText(
    @StringRes textResId: Int,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    BodyLargeText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun BodyLargeText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun BodyMediumText(
    @StringRes textResId: Int,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    BodyLargeText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun BodyMediumText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun BodySmallText(
    @StringRes textResId: Int,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    BodySmallText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun BodySmallText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun LabelLargeText(
    @StringRes textResId: Int,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    LabelLargeText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun LabelLargeText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun LabelMediumText(
    @StringRes textResId: Int,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    LabelMediumText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun LabelMediumText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun LabelSmallText(
    @StringRes textResId: Int,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    LabelSmallText(
        text = stringResource(textResId),
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textDecoration = textDecoration
    )
}

@Composable
fun LabelSmallText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = fontWeight,
        textDecoration = textDecoration
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
