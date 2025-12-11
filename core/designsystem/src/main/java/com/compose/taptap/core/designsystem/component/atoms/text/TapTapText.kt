package com.compose.taptap.core.designsystem.component.atoms.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun TapTapText(
    text: String,
    modifier: Modifier = Modifier,
    variant: TapTapTextVariant = TapTapTextVariant.BASE,
    color: Color = Color.Unspecified,
    style: TextStyle? = null,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    val baseStyle = variant.toTextStyle()
    val mergedStyle = if (style != null) baseStyle.merge(style) else baseStyle

    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = mergedStyle,
        fontWeight = fontWeight,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = onTextLayout
    )
}

@Composable
fun TapTapText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    variant: TapTapTextVariant = TapTapTextVariant.BASE,
    color: Color = Color.Unspecified,
    style: TextStyle? = null,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    val baseStyle = variant.toTextStyle()
    val mergedStyle = if (style != null) baseStyle.merge(style) else baseStyle

    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = mergedStyle,
        fontWeight = fontWeight,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = onTextLayout
    )
}

enum class TapTapTextVariant {
    XS, SM, BASE, LG, XL, XXL;

    @Composable
    fun toTextStyle(): TextStyle {
        return when (this) {
            XS -> TapTapTheme.typography.bodySmall
            SM -> TapTapTheme.typography.bodyMedium
            BASE -> TapTapTheme.typography.bodyLarge
            LG -> TapTapTheme.typography.titleLarge
            XL -> TapTapTheme.typography.headlineMedium
            XXL -> TapTapTheme.typography.headlineLarge
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TapTapTextPreview() {
    TapTapTheme {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            TapTapText(text = "XS - Body Small", variant = TapTapTextVariant.XS)
            TapTapText(text = "SM - Body Medium", variant = TapTapTextVariant.SM)
            TapTapText(text = "BASE - Body Large", variant = TapTapTextVariant.BASE)
            TapTapText(text = "LG - Title Large", variant = TapTapTextVariant.LG)
            TapTapText(text = "XL - Headline Medium", variant = TapTapTextVariant.XL)
            TapTapText(text = "XXL - Headline Large", variant = TapTapTextVariant.XXL)
            
            TapTapText(
                text = "Override Bold",
                variant = TapTapTextVariant.BASE,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
