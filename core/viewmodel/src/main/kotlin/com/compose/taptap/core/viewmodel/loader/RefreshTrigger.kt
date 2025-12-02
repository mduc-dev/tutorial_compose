package com.compose.taptap.core.viewmodel.loader

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * Simple trigger used by ViewModels to request a refresh from UI.
 */
class RefreshTrigger {
    private val _events = MutableSharedFlow<Unit>()
    val events: SharedFlow<Unit> = _events

    suspend fun refresh() {
        _events.emit(Unit)
    }
}

fun newRefreshTrigger(): RefreshTrigger = RefreshTrigger()
