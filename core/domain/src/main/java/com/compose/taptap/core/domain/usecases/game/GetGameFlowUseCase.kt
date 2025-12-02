package com.compose.taptap.core.domain.usecases.game

import androidx.paging.PagingData
import com.compose.taptap.core.domain.repository.GamesRepository
import com.compose.taptap.core.domain.usecases.base.BaseFlowUseCase
import com.compose.taptap.core.model.GetGamesParams
import com.compose.taptap.core.model.ListGameItem
import kotlinx.coroutines.flow.Flow

/**
 * Use case for getting paginated games list with filtering and sorting.
 * 
 * @param repository Games repository for data access
 */
class GetGameFlowUseCase(
    private val repository: GamesRepository,
) : BaseFlowUseCase<GetGamesParams, PagingData<ListGameItem>>() {

    override fun execute(parameters: GetGamesParams): Flow<PagingData<ListGameItem>> =
        repository.gamesStream(
            category = parameters.category,
            sortBy = parameters.sortBy,
            filterType = parameters.filterType
        )
}
