package com.compose.taptap.ui.launcher.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.taptap.core.designsystem.util.LoadingResult
import com.compose.taptap.core.domain.usecases.search.GetSearchPlaceholderFlowUseCase
import com.compose.taptap.core.network.model.SearchResponse
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class SearchViewModel(
    getSearchPlaceholderUseCase: GetSearchPlaceholderFlowUseCase,
) : ViewModel() {
    val searchUiState: StateFlow<LoadingResult<SearchResponse>> =
        getSearchPlaceholderUseCase.execute(Unit)
            .map<SearchResponse, LoadingResult<SearchResponse>> { LoadingResult.Success(it) }
            .onStart { emit(LoadingResult.Loading) }
            .catch { emit(LoadingResult.Failure(it)) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = LoadingResult.Loading
            )
}
