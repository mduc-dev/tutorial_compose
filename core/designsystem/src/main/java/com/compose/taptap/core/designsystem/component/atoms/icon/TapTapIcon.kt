package com.compose.taptap.core.designsystem.component.atoms.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun TapTapIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    size: Dp? = null
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = if (size != null) modifier.size(size) else modifier,
        tint = tint
    )
}

@Composable
fun TapTapIcon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    size: Dp? = null
) {
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = if (size != null) modifier.size(size) else modifier,
        tint = tint
    )
}

@Composable
fun TapTapIcon(
    @DrawableRes id: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    size: Dp? = null
) {
    Icon(
        painter = painterResource(id = id),
        contentDescription = contentDescription,
        modifier = if (size != null) modifier.size(size) else modifier,
        tint = tint
    )
}
