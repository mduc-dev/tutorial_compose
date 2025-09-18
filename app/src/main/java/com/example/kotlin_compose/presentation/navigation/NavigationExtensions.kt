package com.example.kotlin_compose.presentation.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry

internal val NavBackStackEntry.lifecycleIsResumed: Boolean
    get() = lifecycle.currentState == Lifecycle.State.RESUMED