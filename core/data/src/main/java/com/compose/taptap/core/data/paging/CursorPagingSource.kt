package com.compose.taptap.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * Created by duc on 21/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

/**
 * Generic PagingSource implementation for cursor-based pagination.
 *
 * Supply a [loader] lambda that returns a [CursorPage] for the provided cursor key.
 *
 * @param T the type of item being paged.
 */
class CursorPagingSource<T : Any>(
    private val loader: suspend (cursor: String?) -> CursorPage<T>
) : PagingSource<String, T>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, T> = try {
        val page = loader(params.key)
        LoadResult.Page(
            data = page.items,
            prevKey = page.prevCursor,
            nextKey = page.nextCursor
        )
    } catch (throwable: Throwable) {
        LoadResult.Error(throwable)
    }

    override fun getRefreshKey(state: PagingState<String, T>): String? {
        val anchorPosition = state.anchorPosition ?: return null
        val closestPage = state.closestPageToPosition(anchorPosition)
        return closestPage?.prevKey ?: closestPage?.nextKey
    }
}

/**
 * Represents a cursor page payload with optional previous/next cursors.
 */
data class CursorPage<T>(
    val items: List<T>,
    val prevCursor: String? = null,
    val nextCursor: String? = null,
)
