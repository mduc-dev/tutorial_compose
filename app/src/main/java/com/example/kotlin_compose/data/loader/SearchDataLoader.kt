package com.example.kotlin_compose.data.loader

import com.example.kotlin_compose.domain.models.Games
import com.example.kotlin_compose.domain.repositories.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface SearchDataLoader {
    fun loadAndObserveSearch(
        coroutineScope: CoroutineScope,
        refreshTrigger: RefreshTrigger,
        onRefreshFailure: (Throwable) -> Unit,
    ): StateFlow<LoadingResult<List<Games>>>
}

class DefaultSearchDataLoader(
    private val searchRepository: SearchRepository,
    private val dataLoader: DataLoader<List<Games>> = DataLoader(),
) : SearchDataLoader {

    override fun loadAndObserveSearch(
        coroutineScope: CoroutineScope,
        refreshTrigger: RefreshTrigger,
        onRefreshFailure: (Throwable) -> Unit,
    ): StateFlow<LoadingResult<List<Games>>> {
        return dataLoader.loadAndObserveDataAsState(
            coroutineScope = coroutineScope,
            refreshTrigger = refreshTrigger,
            initialData = loading(),
            observeData = { searchRepository.observeGames() },
            fetchData = {
                searchRepository.refreshGames()
            },
            onRefreshFailure = onRefreshFailure,
        )
    }
}
