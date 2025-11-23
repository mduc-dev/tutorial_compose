package com.compose.taptap.feature.game

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.compose.taptap.core.domain.usecases.game.GetGameFlowUseCase
import com.compose.taptap.core.model.GameFilterType
import com.compose.taptap.core.model.GameSortType
import com.compose.taptap.core.model.GetGamesParams
import com.compose.taptap.core.model.ListGameItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ============================================================================
// UI State Models
// ============================================================================

/**
 * Represents the UI state for the Game screen.
 * This is a pure data class with no business logic.
 */
data class GameUiState(
    val searchPlaceholder: String,
    val unreadNotifications: Int,
    val selectedTopTab: Int = 0,
    val selectedSubTab: Int = 0,
    val isLoading: Boolean = false
)

/**
 * Sealed interface representing all possible user interactions on the Game screen.
 * This enables type-safe event handling and makes the UI completely stateless.
 */
sealed interface GameUiEvent {
    data object OnSearchClick : GameUiEvent
    data object OnNotificationClick : GameUiEvent
    data class OnTopTabClick(val index: Int) : GameUiEvent
    data class OnSubTabClick(val index: Int) : GameUiEvent
    data class OnGameClick(val gameId: String) : GameUiEvent
    data class OnCategoryClick(val categoryId: String) : GameUiEvent
    data object OnRetry : GameUiEvent
}

// ============================================================================
// ViewModel Events (One-Time Events)
// ============================================================================

/**
 * One-time events emitted by ViewModel to UI.
 * These are consumed once and don't persist in state.
 */
@Immutable
sealed interface GameEvent {
    data object ShowRefreshFailure : GameEvent
    data class ShowError(val message: String) : GameEvent
    data class NavigateToDetail(val gameId: String) : GameEvent
}

// ============================================================================
// ViewModel
// ============================================================================

@Stable
class GameViewModel(
    private val getGameUseCase: GetGameFlowUseCase,
) : ViewModel() {

    // One-time events channel
    private val _events = Channel<GameEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()
    
    // Filter state - triggers new data fetch when changed
    private val _filterState = MutableStateFlow(GetGamesParams())
    val filterState = _filterState.asStateFlow()

    // Games flow - automatically updates when filter changes
    val gameUiStateFlow = _filterState
        .flatMapLatest { params -> 
            getGameUseCase.execute(params) 
        }
        .cachedIn(viewModelScope)

    /**
     * Update filter parameters and refresh list.
     */
    fun updateFilters(
        category: String? = null,
        sortBy: GameSortType? = null,
        filterType: GameFilterType? = null
    ) {
        _filterState.update { current ->
            current.copy(
                category = category ?: current.category,
                sortBy = sortBy ?: current.sortBy,
                filterType = filterType ?: current.filterType
            )
        }
    }
    
    /**
     * Reset filters to default values.
     */
    fun resetFilters() {
        _filterState.value = GetGamesParams()
    }

    /**
     * Send one-time event to UI.
     */
    private fun sendEvent(event: GameEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }
}
