package com.compose.taptap.data.util
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Created by duc on 16/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */


/**
 * A trigger that can be used to coordinate refresh events across the app.
 * Multiple components can observe the same RefreshTrigger to refresh their data simultaneously.
 */
sealed interface RefreshTrigger {

    /**
     * Triggers a refresh event that will be observed by all collectors.
     */
    suspend fun refresh()

}

/**
 * Factory function to create a RefreshTrigger instance.
 */
fun RefreshTrigger(): RefreshTrigger = DefaultRefreshTrigger()

/**
 * Default implementation of RefreshTrigger using SharedFlow.
 */
internal class DefaultRefreshTrigger : RefreshTrigger {

    private val _refreshEvent = MutableSharedFlow<Unit>()
    internal val refreshEvent = _refreshEvent.asSharedFlow()

    override suspend fun refresh() {
        _refreshEvent.emit(Unit)
    }
}