package com.compose.taptap.ui.launcher.search

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.taptap.data.util.LoadingResult
import com.compose.taptap.domain.usecases.search.GetSearchPlaceholderFlowUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@Stable
data class SearchUiState(
    val isLoading: Boolean = false,
    val placeholderText: String = "Discover Superb Games",
    val error: Throwable? = null,
)

class SearchViewModel(
    getSearchPlaceholderUseCase: GetSearchPlaceholderFlowUseCase,
) : ViewModel() {
    val searchUiState =
        getSearchPlaceholderUseCase(Unit).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = LoadingResult.Loading
        )
}