package com.compose.taptap.core.designsystem.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource

val DisableParentPagerSwipeConnection = object : NestedScrollConnection {
    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        // Consume all remaining horizontal scroll to prevent parent pager from seeing it
        return available.copy(y = 0f)
    }
}
