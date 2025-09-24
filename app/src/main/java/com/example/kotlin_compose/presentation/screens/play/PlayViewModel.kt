package com.example.kotlin_compose.presentation.screens.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kotlin_compose.data.network.utils.ApiResult
import com.example.kotlin_compose.domain.repositories.PlayRepository
import com.example.kotlin_compose.presentation.utils.PLayUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class PlayViewModel(private val playRepository: PlayRepository) : ViewModel() {
    private val _playUiState = MutableStateFlow(PLayUiState())
    val playUiState = _playUiState.asStateFlow()

    val instantGames = playRepository.fetchInstantGames()
        .cachedIn(viewModelScope)

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _playUiState.update { it.copy(recently = ApiResult.Error(exception)) }
    }

    init {
//        fetchInstantGames()
    }


//    private fun fetchInstantGames() {
//        val pagingFlow = playRepository.fetchInstantGames().cachedIn(viewModelScope)
//        _playUiState.update { it.copy(games = pagingFlow) }
//    }

//    fun fetchRecentlyInstantsGames() {
//        viewModelScope.launch(coroutineExceptionHandler) {
//            playRepository.fetchRecentlyInstantsGames().collect {
//                _playUiState.value = it
//            }
//        }
//    }
}