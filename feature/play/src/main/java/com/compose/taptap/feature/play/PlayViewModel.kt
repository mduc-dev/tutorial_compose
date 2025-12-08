package com.compose.taptap.feature.play

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.compose.taptap.core.domain.usecases.play.AddGameToHistoryUseCase
import com.compose.taptap.core.domain.usecases.play.GetGameHistoryUseCase
import com.compose.taptap.core.domain.usecases.play.GetPlayGamesFlowUseCase
import com.compose.taptap.core.domain.usecases.play.GetRandomInstantGameUseCase
import com.compose.taptap.core.domain.usecases.play.MarkGameAsPlayedUseCase
import com.compose.taptap.core.model.InstantGameItem
import com.compose.taptap.core.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@Stable
class PlayViewModel(
    private val getPlayGamesFlowUseCase: GetPlayGamesFlowUseCase,
    private val getRandomInstantGameUseCase: GetRandomInstantGameUseCase,
    private val addGameToHistoryUseCase: AddGameToHistoryUseCase,
    private val markGameAsPlayedUseCase: MarkGameAsPlayedUseCase,
    private val getGameHistoryUseCase: GetGameHistoryUseCase
) : BaseViewModel() {
    private val _playUiState = MutableStateFlow(PLayUiState())
    val playUiState = _playUiState.asStateFlow()

    private val _randomInstantGame = stateHolder<String?>(null)
    val randomInstantGame = _randomInstantGame.asStateFlow()

    private var isFetchingRandomGame = false

    val instantGames = flow {
        emitAll(getPlayGamesFlowUseCase.execute(Unit))
    }.cachedIn(viewModelScope)

    val recentlyGames = stateHolder<List<InstantGameItem>>(emptyList())

    fun onPLayGame(game: InstantGameItem) {
        viewModelScope.launch {
            addGameToHistoryUseCase.execute(game)
            markGameAsPlayedUseCase.execute(game.identification)
            // Update local state immediately
            recentlyGames.value = getHistory()
        }
    }

    private suspend fun getHistory() = getGameHistoryUseCase.execute(Unit)

    fun fetchRandomInstantGame() {
        if (isFetchingRandomGame || _randomInstantGame.value != null) return
        isFetchingRandomGame = true
        viewModelScope.launch {
            try {
                val response = getRandomInstantGameUseCase.execute(Unit)
                if (response.success) {
                    val mediumUrl = response.data.info.icon.mediumUrl
                    _randomInstantGame.updateState { mediumUrl }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isFetchingRandomGame = false
            }
        }
    }
}

data class PLayUiState(
    val isLoading: Boolean = false
)
