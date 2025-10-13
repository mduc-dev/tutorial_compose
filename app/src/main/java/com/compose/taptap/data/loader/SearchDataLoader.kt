package com.compose.taptap.data.loader

import com.compose.taptap.domain.models.Games
import com.compose.taptap.domain.repositories.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface SearchDataLoader {
    fun loadAndObserveSearch(
        coroutineScope: CoroutineScope,
        refreshTrigger: RefreshTrigger,
        onRefreshFailure: (Throwable) -> Unit,
    ): Any
}

class DefaultSearchDataLoader(
    private val searchRepository: SearchRepository,
    private val dataLoader: DataLoader<List<Games>> = DataLoader(),
) : SearchDataLoader {

    override fun loadAndObserveSearch(
        coroutineScope: CoroutineScope,
        refreshTrigger: RefreshTrigger,
        onRefreshFailure: (Throwable) -> Unit,
    ) {
        return
    }
}
