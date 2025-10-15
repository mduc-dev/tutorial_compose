package com.compose.taptap.ui.launcher.search

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.taptap.data.loader.LoadingResult
import com.compose.taptap.data.loader.RefreshTrigger
import com.compose.taptap.data.loader.SearchDataLoader
import com.compose.taptap.data.network.utils.ApiResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Stable
data class SearchUiState(
    val isLoading: Boolean = false,
    val placeholderText: String = "Discover Superb Games",
    val error: Throwable? = null,
)

class SearchViewModel(
    searchDataLoader: SearchDataLoader,
    private val refreshTrigger: RefreshTrigger,
) : ViewModel() {

    val data = searchDataLoader.loadSearchPlaceholder(
        coroutineScope = viewModelScope,
        refreshTrigger = refreshTrigger,
        onRefreshFailure = { throwable ->
            println(throwable)
        })

    val searchUiState = data.map { result ->
        when (result) {
            is LoadingResult.Loading -> SearchUiState(isLoading = true)
            is LoadingResult.Success -> {
                when (val api = result.value) {
                    is ApiResult.Success -> {
                        val text = api.data.firstTextOrDefault()
                        SearchUiState(isLoading = false, placeholderText = text)
                    }

                    is ApiResult.Error -> SearchUiState(error = api.exception)
                    else -> SearchUiState(isLoading = true)
                }
            }

            is LoadingResult.Failure -> SearchUiState(error = result.throwable)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SearchUiState(isLoading = true)
    )

    init {
        viewModelScope.launch {
            println("🚀 Manually triggering fetchSearchPlaceholder")
            refreshTrigger.refresh()
        }
    }
}