package com.compose.taptap.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.taptap.ui.theme.BorderColor
import com.compose.taptap.ui.theme.PPNeu
import com.compose.taptap.ui.theme.PrimaryColor
import com.compose.taptap.ui.theme.PrimaryMaterialDark

@Composable
fun Input(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    border: BorderStroke = BorderStroke(1.dp, color = BorderColor),
    shape: RoundedCornerShape = RoundedCornerShape(24.dp),
    innerPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
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

    BasicTextField(
        modifier = modifier
            .border(border = border, shape = shape)
            .clip(shape = shape)
            .background(color = PrimaryMaterialDark)
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
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = PPNeu,
            color = Color.White,
            textDirection = TextDirection.Content
        ),
        cursorBrush = SolidColor(PrimaryColor),
        decorationBox = { innerTextField -> decorationBox(innerTextField) },
        maxLines = maxLines,
        singleLine = maxLines == 1,
        enabled = enabled,
        keyboardOptions = keyboardOptions,
    )

}
