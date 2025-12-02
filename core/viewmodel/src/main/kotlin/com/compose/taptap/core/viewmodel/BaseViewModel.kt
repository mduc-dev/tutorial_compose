package com.compose.taptap.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * Base class for ViewModels in tutorial_compose.
 * Provides small helpers for local state and collecting flows in the ViewModel scope.
 */
abstract class BaseViewModel : ViewModel() {

    /** Create a protected state holder that callers can expose as StateFlow. */
    protected fun <T> stateHolder(initial: T): MutableStateFlow<T> = MutableStateFlow(initial)

    /** Convenience for in-place state updates. */
    protected fun <T> MutableStateFlow<T>.updateState(reducer: (T) -> T) {
        update(reducer)
    }

    /** Collect a flow into a StateFlow tied to the ViewModel's scope. */
    protected fun <T> Flow<T>.stateInViewModel(
        initial: T,
        started: SharingStarted = SharingStarted.WhileSubscribed(5_000),
    ): StateFlow<T> = stateIn(viewModelScope, started, initial)
}
