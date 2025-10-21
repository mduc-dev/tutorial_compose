package com.compose.taptap.core.data.repository.game

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.compose.taptap.core.data.paging.CursorPage
import com.compose.taptap.core.data.paging.CursorPagingSource
import com.compose.taptap.core.model.ListGameItem
import com.compose.taptap.core.network.service.TapTapService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GamesRepositoryImpl(
    private val tapTapService: TapTapService,
    private val dispatcher: CoroutineDispatcher,
) : GamesRepository {
    override fun gamesStream(): Flow<PagingData<ListGameItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CursorPagingSource { cursor ->
                    val response = tapTapService.getGames(cursor)
                    val data = response.data
                    CursorPage(
                        items = data.list,
                        prevCursor = data.prevPage.takeIf { it.isNotBlank() },
                        nextCursor = data.nextPage.takeIf { it.isNotBlank() },
                    )
                }
            }
        ).flow.flowOn(dispatcher)
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}
