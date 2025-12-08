package com.compose.taptap.core.designsystem.util

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.round
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

fun Modifier.bouncingEffect(): Modifier = composed {
    val offset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }
    val scope = rememberCoroutineScope()

    val connection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // If we have an offset (bounced), consume the scroll to reduce the offset
                val currentOffset = offset.value
                return if (currentOffset.getDistance() > 0) {
                    val consumedX = if (currentOffset.x > 0) {
                        // Bounced to the right (left edge), scrolling right -> consume
                        if (available.x < 0) available.x.coerceAtLeast(-currentOffset.x) else 0f
                    } else if (currentOffset.x < 0) {
                        // Bounced to the left (right edge), scrolling left -> consume
                        if (available.x > 0) available.x.coerceAtMost(-currentOffset.x) else 0f
                    } else 0f

                    val consumedY = if (currentOffset.y > 0) {
                        if (available.y < 0) available.y.coerceAtLeast(-currentOffset.y) else 0f
                    } else if (currentOffset.y < 0) {
                        if (available.y > 0) available.y.coerceAtMost(-currentOffset.y) else 0f
                    } else 0f
                    
                    val consumed = Offset(consumedX, consumedY)
                    
                    // Apply change efficiently
                    scope.launch {
                        offset.snapTo(offset.value + consumed)
                    }
                    
                    consumed
                } else {
                    Offset.Zero
                }
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                // Determine drag multiplier for resistance
                val resistance = 0.5f 
                
                // Only allow overscroll during Drag. Fling events should not create new overscroll 
                // linearly as it leads to massive offsets (flying off screen).
                if (source == NestedScrollSource.Drag) {
                    val newOffset = offset.value + available * resistance
                    scope.launch {
                        offset.snapTo(newOffset)
                    }
                }
                
                return available
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                // If we are bounced, don't fling, just snap back
                if (offset.value.getDistance() > 0) {
                    scope.launch {
                        offset.animateTo(
                            targetValue = Offset.Zero,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                    }
                    return available // Consume all fling velocity if we are bouncing back
                }
                return Velocity.Zero
            }
            
            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                 // Snap back if there is any remaining offset (though PreFling should catch main cases)
                 scope.launch {
                    offset.animateTo(
                        targetValue = Offset.Zero,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy, 
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
                return super.onPostFling(consumed, available)
            }
        }
    }

    this
        .offset { offset.value.round() }
        .nestedScroll(connection)
}
