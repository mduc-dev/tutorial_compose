package com.compose.taptap.core.designsystem.component.atoms.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.taptap.core.designsystem.theme.TapTapShape
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.util.nonScaledSp

data class TapTapButtonPreviewParams(
    val label: String,
    val isLoading: Boolean,
    val size: ButtonSize,
    val variant: Variant,
)

class TapTapButtonPreviewProvider : PreviewParameterProvider<TapTapButtonPreviewParams> {
    override val values: Sequence<TapTapButtonPreviewParams> = sequenceOf(
        TapTapButtonPreviewParams("Solid SM", false, ButtonSize.SM, Variant.SOLID),
        TapTapButtonPreviewParams("Bordered MD", false, ButtonSize.MD, Variant.BORDERED),
        TapTapButtonPreviewParams("Loading LG", true, ButtonSize.LG, Variant.SOLID),
        TapTapButtonPreviewParams("Light LG", false, ButtonSize.LG, Variant.LIGHT),
        TapTapButtonPreviewParams("Flat SM", false, ButtonSize.SM, Variant.FLAT),
        TapTapButtonPreviewParams("Faded MD", false, ButtonSize.MD, Variant.FADED),
        TapTapButtonPreviewParams("Shadow LG", false, ButtonSize.LG, Variant.SHADOW),
    )
}

@Preview
@Composable
fun PreviewTapTapButton(
    @PreviewParameter(TapTapButtonPreviewProvider::class) params: TapTapButtonPreviewParams
) {
    TapTapTheme(darkTheme = true, dynamicColor = false) {
        TapTapButton(
            label = params.label,
            isLoading = params.isLoading,
            size = params.size,
            variant = params.variant,
            onPress = {})
    }
}

enum class Variant { SOLID, BORDERED, LIGHT, FLAT, FADED, SHADOW }

enum class ButtonSize(val size: Dp) {
    SM(ButtonDefaults.MinHeight), MD(ButtonDefaults.MinHeight), LG(ButtonDefaults.MinHeight);

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
        SM -> TapTapTheme.typography.bodySmall
        MD -> TapTapTheme.typography.bodyMedium
        LG -> TapTapTheme.typography.bodyLarge
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TapTapButton(
    modifier: Modifier = Modifier,
    label: String,
    variant: Variant = Variant.SOLID,
    size: ButtonSize = ButtonSize.SM,
    isLoading: Boolean? = false,
    enable: Boolean = true,
    shape: Shape = TapTapShape.corners.pill,
    containerColor: Color? = null,
    contentColor: Color? = null,
    onPress: () -> Unit
) {
    val rippleConfiguration = RippleConfiguration(
        color = TapTapTheme.colors.onSurface, rippleAlpha = RippleAlpha(
            draggedAlpha = 0.16f, focusedAlpha = 0.12f, hoveredAlpha = 0.08f, pressedAlpha = 0.24f
        )
    )

    val defaultContainerColor = when (variant) {
        Variant.SOLID -> TapTapTheme.colors.primary
        Variant.LIGHT -> TapTapTheme.colors.surface
        else -> Color.Transparent
    }

    val finalContainerColor = containerColor ?: defaultContainerColor

    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = finalContainerColor,
        disabledContainerColor = finalContainerColor.copy(alpha = if (variant == Variant.SOLID || variant == Variant.LIGHT) 0.3f else 0.12f),
        contentColor = contentColor
            ?: if (variant == Variant.SOLID) TapTapTheme.colors.onPrimary else TapTapTheme.colors.onSurface
    )

    CompositionLocalProvider(LocalRippleConfiguration provides rippleConfiguration) {
        Button(
            onClick = { onPress() },
            modifier = modifier
                .height(size.size)
                .testTag("taptap_button"),
            colors = buttonColors,
            enabled = enable,
            shape = shape,
            border = if (variant == Variant.BORDERED) BorderStroke(
                width = 0.5.dp,
                color = TapTapTheme.colors.onSurface.copy(alpha = 0.2f)
            ) else null
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(TapTapTheme.spacing.small)
            ) {
                if (isLoading == true) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        color = TapTapTheme.colors.onPrimary,
                        strokeWidth = 2.dp,
                        trackColor = TapTapTheme.colors.surface,
                    )
                }
                Text(
                    text = label,
                    style = size.textStyle(),
                    fontSize = 15.sp.nonScaledSp,
                    color = contentColor
                        ?: (if (variant == Variant.SOLID) TapTapTheme.colors.onPrimary else TapTapTheme.colors.onSurface)
                )
            }
        }
    }
}
