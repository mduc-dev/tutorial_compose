package com.compose.taptap.core.designsystem.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

private val DefaultShapeTokens = ShapeTokens()

val TapTapShapes = Shapes(
    small = DefaultShapeTokens.small,
    medium = DefaultShapeTokens.medium,
    large = DefaultShapeTokens.large
)

@Immutable
data class ShapeTokens(
    val extraSmall: CornerBasedShape = RoundedCornerShape(2.dp),
    val small: CornerBasedShape = RoundedCornerShape(4.dp),
    val medium: CornerBasedShape = RoundedCornerShape(8.dp),
    val large: CornerBasedShape = RoundedCornerShape(12.dp),
    val pill: CornerBasedShape = RoundedCornerShape(50.dp),
    val card: CornerBasedShape = RoundedCornerShape(16.dp),
    val dialog: CornerBasedShape = RoundedCornerShape(20.dp),
)

internal val LocalShapeTokens = staticCompositionLocalOf { DefaultShapeTokens }

object TapTapShape {
    val corners: ShapeTokens
        @Composable get() = LocalShapeTokens.current
}
