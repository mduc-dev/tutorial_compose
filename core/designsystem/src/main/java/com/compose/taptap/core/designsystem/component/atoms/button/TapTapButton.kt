package com.compose.taptap.core.designsystem.component.atoms.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
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
import com.compose.taptap.core.designsystem.theme.TapTapDimens
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
        TapTapButtonPreviewParams("Light XL", false, ButtonSize.XL, Variant.LIGHT),
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

enum class ButtonSize {
    SM, MD, LG, XL;

    val size: Dp
        @Composable
        get() = when (this) {
            SM -> TapTapTheme.spacing.xLarge
            MD -> TapTapTheme.spacing.xxLarge
            LG -> TapTapDimens.ButtonHeight
            XL -> TapTapDimens.FieldMinHeight
        }

    fun iconSize(spacing: com.compose.taptap.core.designsystem.theme.Spacing): Dp = when (this) {
        SM -> spacing.mediumLarge
        MD -> spacing.gutter
        LG, XL -> spacing.large
    }

    fun iconSpace(spacing: com.compose.taptap.core.designsystem.theme.Spacing): Dp = when (this) {
        SM -> spacing.tiny
        MD, LG, XL -> spacing.small
    }

    @Composable
    fun textStyle() = when (this) {
        SM -> TapTapTheme.typography.labelMedium
        MD -> TapTapTheme.typography.bodyMedium
        LG, XL -> TapTapTheme.typography.bodyLarge
    }
}

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
    TapTapButton(
        modifier = modifier,
        variant = variant,
        size = size,
        isLoading = isLoading,
        enable = enable,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        onPress = onPress
    ) {
        if (isLoading == true) {
            CircularProgressIndicator(
                modifier = Modifier.size(size.iconSize(TapTapTheme.spacing)),
                color = contentColor ?: if (variant == Variant.SOLID) TapTapTheme.colors.onPrimary else TapTapTheme.colors.onSurface,
                strokeWidth = TapTapTheme.spacing.xSmall,
                trackColor = TapTapTheme.colors.surface.copy(alpha = 0.5f),
            )
        }
        Text(
            text = label,
            style = size.textStyle(),
            fontSize = 15.sp.nonScaledSp,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TapTapButton(
    modifier: Modifier = Modifier,
    variant: Variant = Variant.SOLID,
    size: ButtonSize = ButtonSize.SM,
    isLoading: Boolean? = false,
    enable: Boolean = true,
    shape: Shape = TapTapShape.corners.pill,
    containerColor: Color? = null,
    contentColor: Color? = null,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues? = null,
    onPress: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    val rippleConfiguration = RippleConfiguration(
        color = TapTapTheme.colors.onSurface, rippleAlpha = RippleAlpha(
            draggedAlpha = 0.16f, focusedAlpha = 0.12f, hoveredAlpha = 0.08f, pressedAlpha = 0.24f
        )
    )

    val defaultContainerColor = when (variant) {
        Variant.SOLID -> TapTapTheme.colors.primary
        Variant.LIGHT, Variant.SHADOW -> TapTapTheme.colors.surface
        Variant.FADED -> TapTapTheme.colors.primary.copy(alpha = 0.1f)
        else -> Color.Transparent
    }

    val finalContainerColor = containerColor ?: defaultContainerColor

    val defaultContentColor = when (variant) {
        Variant.SOLID -> TapTapTheme.colors.onPrimary
        else -> TapTapTheme.colors.onSurface
    }

    val finalContentColor = contentColor ?: defaultContentColor

    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = finalContainerColor,
        disabledContainerColor = finalContainerColor.copy(alpha = if (variant == Variant.SOLID || variant == Variant.LIGHT || variant == Variant.FADED) 0.3f else 0.12f),
        contentColor = finalContentColor,
        disabledContentColor = finalContentColor.copy(alpha = 0.38f)
    )

    val finalElevation: ButtonElevation? = elevation ?: if (variant == Variant.SHADOW) {
        ButtonDefaults.buttonElevation(defaultElevation = TapTapTheme.spacing.xSmall)
    } else {
        ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
            disabledElevation = 0.dp
        )
    }
    
    val finalBorder = border ?: if (variant == Variant.BORDERED) BorderStroke(
        width = 1.dp, // 1.dp is standard hairline, can use TapTapTheme.spacing.xSmall / 2 if strictly needed but Dp(1f) is clearer
        color = TapTapTheme.colors.outline.copy(alpha = 0.5f)
    ) else null

    CompositionLocalProvider(LocalRippleConfiguration provides rippleConfiguration) {
        Button(
            onClick = { onPress() },
            modifier = modifier
                .height(size.size)
                .testTag("taptap_button"),
            colors = buttonColors,
            elevation = finalElevation,
            enabled = enable,
            shape = shape,
            contentPadding = contentPadding ?: PaddingValues(horizontal = TapTapTheme.spacing.mediumLarge),
            border = finalBorder
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(size.iconSpace(TapTapTheme.spacing))
            ) {
                content()
            }
        }
    }
}
