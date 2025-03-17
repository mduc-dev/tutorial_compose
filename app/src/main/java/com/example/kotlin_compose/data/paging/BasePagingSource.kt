package com.example.kotlin_compose.data.paging

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