package com.compose.taptap.core.data.repository.game

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.compose.taptap.core.data.paging.CursorPage
import com.compose.taptap.core.data.paging.CursorPagingSource
import com.compose.taptap.core.domain.repository.GamesRepository
import com.compose.taptap.core.model.GameFilterType
import com.compose.taptap.core.model.GameSortType
import com.compose.taptap.core.model.ListGameItem
import com.compose.taptap.core.network.service.TapTapService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class GamesRepositoryImpl(
    private val tapTapService: TapTapService,
    private val dispatcher: CoroutineDispatcher,
) : GamesRepository {
    
    override fun gamesStream(
        category: String?,
        sortBy: GameSortType,
        filterType: GameFilterType
    ): Flow<PagingData<ListGameItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CursorPagingSource { cursor ->
                    // TODO: Pass category, sortBy, filterType to API when backend supports it
                    val response = tapTapService.getGames(cursor)
                    val data = response.data
                    CursorPage(
                        items = data.list,
                        prevCursor = data.prevPage.takeIf { it.isNotBlank() },
                        nextCursor = data.nextPage.takeIf { it.isNotBlank() }
                    )
                }
            }
        ).flow.flowOn(dispatcher)
    }
    
    override suspend fun refreshGames(): Result<Unit> = withContext(dispatcher) {
        try {
            // TODO: Implement cache invalidation when local database is added
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 5
        private const val PREFETCH_DISTANCE = 1
        private const val INITIAL_LOAD_SIZE = 5
    }
}
