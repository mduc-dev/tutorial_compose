package com.compose.taptap.ui.launcher.game

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.taptap.data.loader.RefreshTrigger
import com.compose.taptap.data.loader.loading
import com.compose.taptap.data.mappers.GamesDataMapper
import com.compose.taptap.domain.usecases.game.ObserveGameUseCase
import com.compose.taptap.domain.usecases.game.ObserveGamesInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@Immutable
sealed interface GameEvent {
    data object ShowRefreshFailure : GameEvent
}

@Stable
class GameViewModel(
    observeGamesUseCase: ObserveGameUseCase,
    private val gamesDataMapper: GamesDataMapper,
    private val refreshTrigger: RefreshTrigger,
) : ViewModel() {

    private val _event = MutableStateFlow<GameEvent?>(null)
    val event = _event.asStateFlow()

    private val data = observeGamesUseCase.execute(
        ObserveGamesInput(refreshTrigger = refreshTrigger)
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = loading()
    )

    val gameUiState = data.map { gamesDataMapper.map(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = gamesDataMapper.map(data.value)
    )

    fun refresh() {
        viewModelScope.launch {
            refreshTrigger.refresh()
        }
    }

    fun consumeEvent() {
        _event.update { null }
    }
}
