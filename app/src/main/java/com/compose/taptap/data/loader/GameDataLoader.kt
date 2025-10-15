package com.compose.taptap.data.loader

import com.compose.taptap.domain.models.Games
import com.compose.taptap.domain.repositories.GamesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface GamesDataLoader {
    fun loadAndObserveGames(
        coroutineScope: CoroutineScope,
        refreshTrigger: RefreshTrigger,
        onRefreshFailure: (Throwable) -> Unit,
    ): StateFlow<LoadingResult<List<Games>>>
}

class DefaultGamesDataLoader(
    private val gamesRepository: GamesRepository,
    private val dataLoader: DataLoader<List<Games>> = DataLoader(),
) : GamesDataLoader {

    override fun loadAndObserveGames(
        coroutineScope: CoroutineScope,
        refreshTrigger: RefreshTrigger,
        onRefreshFailure: (Throwable) -> Unit,
    ): StateFlow<LoadingResult<List<Games>>> {
        return dataLoader.loadAndObserveDataAsState(
            coroutineScope = coroutineScope,
            refreshTrigger = refreshTrigger,
            initialData = loading(),
            observeData = { gamesRepository.observeGames() },
            fetchData = {
                gamesRepository.refreshGames()
            },
            onRefreshFailure = onRefreshFailure,
        )
    }
}
