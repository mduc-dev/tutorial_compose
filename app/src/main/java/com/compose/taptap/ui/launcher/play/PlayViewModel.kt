package com.compose.taptap.ui.launcher.play

import InstantGameItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.compose.taptap.data.repository.PlayRepository
import com.compose.taptap.ui.utils.PLayUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class PlayViewModel(private val playRepository: PlayRepository) : ViewModel() {
    private val _playUiState = MutableStateFlow(PLayUiState())
    val playUiState = _playUiState.asStateFlow()

    val instantGames = playRepository.fetchInstantGames()
        .cachedIn(viewModelScope)


    fun onPLayGame(game: InstantGameItem) {
        playRepository.addToHistory(game)
        playRepository.markPlayed(game.identification)
    }

    fun getHistory() = playRepository.getHistory()

}