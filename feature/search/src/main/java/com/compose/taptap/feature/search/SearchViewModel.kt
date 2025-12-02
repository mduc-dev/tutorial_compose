package com.compose.taptap.feature.search

import androidx.lifecycle.viewModelScope
import com.compose.taptap.core.designsystem.util.LoadingResult
import com.compose.taptap.core.domain.usecases.search.GetSearchPlaceholderFlowUseCase
import com.compose.taptap.core.model.Search
import com.compose.taptap.core.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class SearchViewModel(
    getSearchPlaceholderUseCase: GetSearchPlaceholderFlowUseCase,
) : BaseViewModel() {
    val searchUiState: StateFlow<LoadingResult<Search>> =
        getSearchPlaceholderUseCase.execute(Unit)
            .map<Search, LoadingResult<Search>> { LoadingResult.Success(it) }
            .onStart { emit(LoadingResult.Loading) }
            .catch { emit(LoadingResult.Failure(it)) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = LoadingResult.Loading
            )
}
