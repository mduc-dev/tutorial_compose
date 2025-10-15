package com.compose.taptap.data.loader

import com.compose.taptap.data.network.models.SearchPlaceHolder
import com.compose.taptap.data.network.utils.ApiResult
import com.compose.taptap.domain.usecases.search.FetchSearchPlaceholderUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first

interface SearchDataLoader {
    fun loadSearchPlaceholder(
        coroutineScope: CoroutineScope,
        refreshTrigger: RefreshTrigger? = null,
        onRefreshFailure: (Throwable) -> Unit = {},
    ): StateFlow<LoadingResult<ApiResult<SearchPlaceHolder>>>
}

class DefaultSearchDataLoader(
    private val fetchSearchPlaceholderUseCase: FetchSearchPlaceholderUseCase,
    private val dataLoader: DataLoader<ApiResult<SearchPlaceHolder>> = DataLoader(),
) : SearchDataLoader {

    override fun loadSearchPlaceholder(
        coroutineScope: CoroutineScope,
        refreshTrigger: RefreshTrigger?,
        onRefreshFailure: (Throwable) -> Unit,
    ): StateFlow<LoadingResult<ApiResult<SearchPlaceHolder>>> {

        return dataLoader.loadAndObserveDataAsState(
            coroutineScope = coroutineScope,
            refreshTrigger = refreshTrigger,
            initialData = loading(),
            fetchData = { previous ->
                runCatching {
                    fetchSearchPlaceholderUseCase.execute(Unit).first()
                }
            },
            onRefreshFailure = onRefreshFailure,
        )
    }
}
