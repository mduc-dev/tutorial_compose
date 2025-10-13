package com.compose.taptap.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class BasePagingSource<T : Any>(val fetchData: suspend (page: Int) -> List<T>?) :
    PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        val data = fetchData(page) ?: emptyList()

        return try {
            LoadResult.Page(data = data, prevKey = null, nextKey = page + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}


class CursorPagingSource<T : Any>(
    private val fetchData: suspend (cursor: String?) -> Pair<List<T>, String?>
) : PagingSource<String, T>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, T> {
        return try {
            val cursor = params.key
            val (data, nextUrl) = fetchData(cursor)

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = if (nextUrl.isNullOrBlank() || nextUrl == cursor) null else nextUrl
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, T>): String? = null
}
