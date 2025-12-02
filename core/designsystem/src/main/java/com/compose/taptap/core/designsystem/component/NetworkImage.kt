package com.compose.taptap.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import coil3.compose.AsyncImage

@Composable
fun NetworkImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    alignment: Alignment = Alignment.Center
) {
    val isPreview = LocalInspectionMode.current

    if (isPreview) {
        if (placeholder != null) {
            Image(
                painter = placeholder,
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale,
                alignment = alignment
            )
        } else {
            // Fallback for preview if no placeholder provided
            Image(
                painter = ColorPainter(Color.Gray),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale,
                alignment = alignment
            )
        }
    } else {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale,
            alignment = alignment,
            placeholder = placeholder
        )
    }
}
