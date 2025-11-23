package com.compose.taptap.core.data.repository.game

import androidx.paging.PagingData
import com.compose.taptap.core.model.GameFilterType
import com.compose.taptap.core.model.GameSortType
import com.compose.taptap.core.model.ListGameItem
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for games data access.
 */
interface GamesRepository {
    
    /**
     * Get paginated stream of games with filtering and sorting.
     */
    fun gamesStream(
        category: String? = null,
        sortBy: GameSortType = GameSortType.POPULAR,
        filterType: GameFilterType = GameFilterType.ALL
    ): Flow<PagingData<ListGameItem>>
    
    /**
     * Refresh games data from remote source.
     */
    suspend fun refreshGames(): Result<Unit>
}
