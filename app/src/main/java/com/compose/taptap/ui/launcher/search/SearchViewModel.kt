package com.compose.taptap.ui.launcher.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.taptap.data.network.models.SearchPlaceHolder
import com.compose.taptap.data.network.utils.ApiResult
import com.compose.taptap.domain.repositories.SearchRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {
    private val _searchUiState = MutableStateFlow<ApiResult<SearchPlaceHolder>>(ApiResult.Loading)
    val searchUiState = _searchUiState.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _searchUiState.update { ApiResult.Error(exception) }
    }

    init {
        fetchSearchPLaceHolder()
    }

    fun fetchSearchPLaceHolder() {
        viewModelScope.launch(coroutineExceptionHandler) {
            searchRepository.fetchSearchPlaceholder().collect {
                _searchUiState.value = it
            }
        }
    }
}