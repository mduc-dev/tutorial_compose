package com.compose.taptap.ui.launcher.game

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.taptap.data.util.LoadingResult
import com.compose.taptap.data.model.ListItem
import com.compose.taptap.domain.usecases.game.GetGameFlowUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


@Immutable
sealed interface GameEvent {
    data object ShowRefreshFailure : GameEvent
}

@Stable
class GameViewModel(
    getGameUseCase: GetGameFlowUseCase,
) : ViewModel() {

    private val _event = MutableStateFlow<GameEvent?>(null)
    val event = _event.asStateFlow()

    val gameUiStateFlow: StateFlow<LoadingResult<List<ListItem>>> = getGameUseCase(Unit)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = LoadingResult.Loading
        )

    fun refresh() {

    }

    fun consumeEvent() {
        _event.update { null }
    }
}
