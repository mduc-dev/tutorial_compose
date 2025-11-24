package com.compose.taptap.feature.game

import androidx.compose.runtime.compositionLocalOf
import androidx.paging.compose.LazyPagingItems
import com.compose.taptap.core.model.ListGameItem

/**
 * CompositionLocal to provide LazyPagingItems throughout the composition tree
 * without prop drilling.
 * 
 * This allows child composables to access the game list directly using
 * `val gameList = LocalGameList.current` instead of passing it through
 * multiple layers of composables.
 */
val LocalGameList = compositionLocalOf<LazyPagingItems<ListGameItem>> {
    error("LocalGameList not provided. Make sure to wrap your composables with CompositionLocalProvider.")
}
