package com.example.kotlin_compose.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kotlin_compose.ui.theme.Black1A
import com.example.kotlin_compose.ui.theme.Black20
import com.example.kotlin_compose.ui.theme.BlackDisable
import com.example.kotlin_compose.ui.theme.BlackF3
import com.example.kotlin_compose.ui.theme.Green1a
import kotlinx.coroutines.delay

//TODO: border {color,width}, disable {color},


enum class Variant { SOLID, BORDERED, LIGHT, FLAT, FADED, SHADOW }

enum class ButtonSize(val size: Dp) {
    SM(ButtonDefaults.MinHeight),
    MD(ButtonDefaults.MinHeight),
    LG(ButtonDefaults.MinHeight);

    fun iconSize(): Dp = when (this) {
        SM -> 16.dp
        MD -> 20.dp
        LG -> 24.dp
    }

    fun iconSpace(): Dp = when (this) {
        SM -> 4.dp
        MD, LG -> 8.dp
    }

    @Composable
    fun textStyle() = when (this) {
        SM -> MaterialTheme.typography.bodySmall
        MD -> MaterialTheme.typography.bodyMedium
        LG -> MaterialTheme.typography.bodyLarge
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DDButton(
    modifier: Modifier = Modifier,
    label: String,
    variant: Variant = Variant.SOLID,
    size: ButtonSize = ButtonSize.SM,
    isLoading: Boolean? = false,
    onPress: () -> Unit
) {
    val rippleConfiguration =
        RippleConfiguration(
            color = BlackDisable, rippleAlpha = RippleAlpha(
                draggedAlpha = 0.16f,
                focusedAlpha = 0.12f,
                hoveredAlpha = 0.08f,
                pressedAlpha = 0.24f
            )
        )

    val buttonColors = when (variant) {
        Variant.SOLID -> ButtonDefaults.buttonColors(
            containerColor = Green1a,
            disabledContainerColor = Black20
        )

        Variant.BORDERED -> ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Black20
        )

        Variant.LIGHT -> ButtonDefaults.buttonColors(
            containerColor = Color.White,
            disabledContainerColor = Black20
        )

        Variant.FLAT -> ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Black20
        )

        Variant.FADED -> ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Black20
        )

        Variant.SHADOW -> ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Black20
        )
    }
    CompositionLocalProvider(LocalRippleConfiguration provides rippleConfiguration) {
        Button(
            onClick = { onPress() },
            modifier = modifier
                .height(size.size)
                .testTag("dd_button"),
            colors = buttonColors,
            shape = RoundedCornerShape(6.dp),
            border = if (variant == Variant.BORDERED) BorderStroke(
                width = 0.5.dp,
                color = BlackF3
            ) else null
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (isLoading == true) {
                    var progress by remember { mutableFloatStateOf(0f) }
                    LaunchedEffect(key1 = true) {
                        while (progress < 1f) {
                            delay(50)
                            progress += 0.01f
                        }
                    }
                    CircularProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        color = Color.White,
                        strokeWidth = 2.dp,
                        trackColor = Black1A,
                    )
                }
                Text(text = label, style = size.textStyle(), color = Color.White)
            }
        }
    }

}


@Preview(apiLevel = 33, showBackground = true, showSystemUi = true)
@Composable
fun PreviewButton() {
    DDButton(
        isLoading = true,
        label = "hello",
        onPress = {},
        size = ButtonSize.LG,
        variant = Variant.SOLID
    )
}