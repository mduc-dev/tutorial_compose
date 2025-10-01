package com.example.kotlin_compose.presentation.screens.game

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_compose.data.loader.GamesDataLoader
import com.example.kotlin_compose.data.loader.RefreshTrigger
import com.example.kotlin_compose.data.mappers.GamesDataMapper
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
    gamesDataLoader: GamesDataLoader,
    private val gamesDataMapper: GamesDataMapper,
    private val refreshTrigger: RefreshTrigger,
) : ViewModel() {

    private val _event = MutableStateFlow<GameEvent?>(null)
    val event = _event.asStateFlow()

    private val data = gamesDataLoader.loadAndObserveGames(
        coroutineScope = viewModelScope,
        refreshTrigger = refreshTrigger,
        onRefreshFailure = { throwable ->
            println(throwable)
            _event.update { GameEvent.ShowRefreshFailure }
        },
    )

    val screenState = data.map { gamesDataMapper.map(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = gamesDataMapper.map(data.value),
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
