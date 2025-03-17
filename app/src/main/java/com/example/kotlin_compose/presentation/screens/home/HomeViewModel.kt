package com.example.kotlin_compose.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kotlin_compose.domain.repositories.GamesRepository
import com.example.kotlin_compose.presentation.utils.HomeUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val gamesRepository: GamesRepository) : ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState(isLoading = true))
    val homeUiState = _homeUiState.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _homeUiState.update { it.copy(isLoading = false, error = exception.message) }
    }

    init {
        fetchTrendingGames()
//        fetchUpcomingGames()
//        fetchPopularGames()
    }


    private fun fetchTrendingGames() = viewModelScope.launch(coroutineExceptionHandler) {
        gamesRepository.fetchTrendingGames().onSuccess { data ->
            _homeUiState.update {
                it.copy(
                    isLoading = false, trendingGames = data.cachedIn(viewModelScope)
                )
            }
        }.onFailure { error ->
            _homeUiState.update { it.copy(error = error.message, isLoading = false) }
        }
    }

    private fun fetchUpcomingGames() = viewModelScope.launch(coroutineExceptionHandler) {
        gamesRepository.fetchUpcomingGames().onSuccess { data ->
            _homeUiState.update { it.copy(isLoading = false, upcomingGames = data) }
        }.onFailure { error ->
            _homeUiState.update { it.copy(error = error.message, isLoading = false) }
        }
    }

    private fun fetchPopularGames() = viewModelScope.launch(coroutineExceptionHandler) {
        gamesRepository.fetchPopularGames().onSuccess { data ->
            _homeUiState.update { it.copy(isLoading = false, popularGames = data) }
        }.onFailure { error ->
            _homeUiState.update { it.copy(error = error.message, isLoading = false) }
        }
    }
}