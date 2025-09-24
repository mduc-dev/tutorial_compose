package com.example.kotlin_compose.presentation.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kotlin_compose.domain.repositories.GamesRepository
import com.example.kotlin_compose.presentation.utils.GameUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val gamesRepository: GamesRepository,
) : ViewModel() {
    private val _gameUiState = MutableStateFlow(GameUiState(isLoading = true))
    val gameUiState = _gameUiState.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _gameUiState.update { it.copy(isLoading = false, error = exception.message) }
    }

    init {
        fetchTrendingGames()
//        fetchSearchPlaceHolder()
//        fetchUpcomingGames()
//        fetchPopularGames()
    }


    private fun fetchTrendingGames() = {
        val pagingFlowGames = gamesRepository.fetchTrendingGames().cachedIn(viewModelScope)
        _gameUiState.update {
            it.copy(
                isLoading = false, trendingGames = pagingFlowGames
            )

        }

    }

//    private fun fetchSearchPlaceHolder() = viewModelScope.launch(coroutineExceptionHandler) {
//        gamesRepository.fetchPlaceholderSearch().onSuccess { data ->
//            _gameUiState.update { it.copy(isLoading = false) }
//        }
//    }

    private fun fetchActionGames() = viewModelScope.launch(coroutineExceptionHandler) {
        gamesRepository.fetchActionGames().onSuccess { data ->
            _gameUiState.update { it.copy(isLoading = false) }
        }
    }

    private fun fetchUpcomingGames() = viewModelScope.launch(coroutineExceptionHandler) {
        gamesRepository.fetchUpcomingGames().onSuccess { data ->
            _gameUiState.update { it.copy(isLoading = false, upcomingGames = data) }
        }
    }

    private fun fetchPopularGames() = viewModelScope.launch(coroutineExceptionHandler) {
        gamesRepository.fetchPopularGames().onSuccess { data ->
            _gameUiState.update { it.copy(isLoading = false, popularGames = data) }
        }
    }
}