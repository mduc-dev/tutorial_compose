package com.compose.taptap.core.designsystem.component.molecules.tabs

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun TapHomeBottomFlipView(
    modifier: Modifier = Modifier,
    isFlipped: Boolean = false,
    backImageUrl: String? = null,
    onFlip: () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = "FlipAnimation"
    )
    val circleColor = TapTapTheme.colors.scrim
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onFlip() },
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 2.dp.toPx()
            val radius = (size.minDimension / 2) - (strokeWidth / 2)
            drawCircle(
                color = circleColor,
                radius = radius,
                style = Stroke(width = strokeWidth)
            )
        }

        // Front View (0 degrees)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                    alpha = if (rotation <= 90f) 1f else 0f
                },
            contentAlignment = Alignment.Center
        ) {
            TapImageView(resId = R.drawable.thi_home_play_random)
        }

        // Back View (180 degrees)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    rotationY = rotation - 180f
                    alpha = if (rotation > 90f) 1f else 0f
                },
            contentAlignment = Alignment.Center
        ) {
            if (backImageUrl != null) {
                AsyncImage(
                    model = backImageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                OverlayIcon()
            } else {
                TapImageView(resId = R.drawable.thi_home_play_random)
                OverlayIcon()
            }
        }
    }
}

@Composable
private fun TapImageView(resId: Int) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun OverlayIcon() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f), CircleShape)
        )
        Image(
            painter = painterResource(id = R.drawable.ico_20_video_playback_play),
            contentDescription = "Play",
            modifier = Modifier.size(20.dp)
        )
    }
}
