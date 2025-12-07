package com.compose.taptap.core.designsystem.component.atoms.textfield

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.theme.Spacing
import com.compose.taptap.core.designsystem.theme.TapTapShape
import com.compose.taptap.core.designsystem.theme.TapTapTheme

private val DefaultInputSpacing = Spacing()

@Composable
fun TapTapTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    border: BorderStroke? = null,
    shape: CornerBasedShape? = null,
    innerPadding: PaddingValues = PaddingValues(
        horizontal = DefaultInputSpacing.mediumLarge,
        vertical = DefaultInputSpacing.small
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit,
) {
    var textState by remember { mutableStateOf(TextFieldValue(text = value)) }

    if (textState.text != value) {
        LaunchedEffect(value) {
            textState = textState.copy(
                text = value,
                selection = TextRange(value.length),
            )
        }
    }

    val description = "Enter your value"
    val resolvedShape = shape ?: TapTapShape.corners.pill
    val resolvedBorder = border ?: BorderStroke(
        width = 1.dp,
        color = TapTapTheme.colors.onSurface.copy(alpha = 0.12f)
    )

    BasicTextField(
        modifier = modifier
            .border(border = resolvedBorder, shape = resolvedShape)
            .clip(shape = resolvedShape)
            .background(color = TapTapTheme.colors.surface)
            .padding(innerPadding)
            .semantics { contentDescription = description },
        value = textState,
        onValueChange = {
            textState = it
            if (value != it.text) {
                onValueChange(it.text)
            }
        },
//        visualTransformation = {
//            val styledText = buildAnnotatedMessageText(
//                text = it.text,
//                textColor = theme.textStyle.color,
//                textFontStyle = typography.body.fontStyle,
//                linkStyle = TextStyle(
//                    color = colors.primaryAccent,
//                    textDecoration = TextDecoration.Underline,
//                ),
//                mentionsColor = colors.primaryAccent,
//            )
//            TransformedText(styledText, OffsetMapping.Identity)
//        },
        visualTransformation = visualTransformation,
        textStyle = TapTapTheme.typography.bodyLarge.copy(color = TapTapTheme.colors.onSurface),
        cursorBrush = SolidColor(TapTapTheme.colors.primary),
        decorationBox = { innerTextField -> decorationBox(innerTextField) },
        maxLines = maxLines,
        singleLine = maxLines == 1,
        enabled = enabled,
        keyboardOptions = keyboardOptions,
    )

}
