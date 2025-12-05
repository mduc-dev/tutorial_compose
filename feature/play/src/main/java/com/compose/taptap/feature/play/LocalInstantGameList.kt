package com.compose.taptap.feature.play

import androidx.compose.runtime.compositionLocalOf
import androidx.paging.compose.LazyPagingItems
import com.compose.taptap.core.model.InstantGameItem

/**
 * CompositionLocal to provide LazyPagingItems throughout the composition tree
 * without prop drilling.
 */
val LocalInstantGameList = compositionLocalOf<LazyPagingItems<InstantGameItem>> {
    error("LocalInstantGameList not provided. Make sure to wrap your composables with CompositionLocalProvider.")
}
