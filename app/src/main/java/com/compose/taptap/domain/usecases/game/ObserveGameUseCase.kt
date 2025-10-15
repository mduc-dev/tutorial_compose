package com.compose.taptap.domain.usecases.game

import com.compose.taptap.data.loader.DataLoader
import com.compose.taptap.data.loader.LoadingResult
import com.compose.taptap.data.loader.RefreshTrigger
import com.compose.taptap.data.loader.loading
import com.compose.taptap.domain.models.Games
import com.compose.taptap.domain.repositories.GamesRepository
import com.compose.taptap.domain.usecases.base.BaseFlowUseCase
import kotlinx.coroutines.flow.Flow

/**
 * Created by duc on 15/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */
class ObserveGameUseCase(
    private val gamesRepository: GamesRepository,
    private val dataLoader: DataLoader<List<Games>> = DataLoader()
) : BaseFlowUseCase<ObserveGamesInput, LoadingResult<List<Games>>>() {

    override fun execute(input: ObserveGamesInput): Flow<LoadingResult<List<Games>>> {
        return dataLoader.loadAndObserveData(
            refreshTrigger = input.refreshTrigger,
            initialData = loading(),
            observeData = { gamesRepository.observeGames() },
            fetchData = { gamesRepository.refreshGames() },
            onRefreshFailure = input.onRefreshFailure ?: { throwable ->
                println("Refresh failed: $throwable")
            }
        )
    }
}

data class ObserveGamesInput(
    val refreshTrigger: RefreshTrigger,
    val onRefreshFailure: ((Throwable) -> Unit)? = null
)
