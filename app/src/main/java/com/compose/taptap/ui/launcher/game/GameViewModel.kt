package com.compose.taptap.ui.launcher.game

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.compose.taptap.core.data.loader.RefreshTrigger
import com.compose.taptap.core.designsystem.util.LoadingResult
import com.compose.taptap.core.designsystem.util.map
import com.compose.taptap.core.domain.usecases.game.GetGameFlowUseCase
import com.compose.taptap.core.model.ListGameItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
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
    getGameUseCase: GetGameFlowUseCase,
) : ViewModel() {

    private val _event = MutableStateFlow<GameEvent?>(null)
    val refreshTrigger = RefreshTrigger()
    val event = _event.asStateFlow()

    val gameUiStateFlow =
        getGameUseCase.execute(Unit).cachedIn(viewModelScope)


    fun refresh() {
        viewModelScope.launch {
            refreshTrigger.refresh()
        }
    }


    fun consumeEvent() {
        _event.update { null }
    }
}
