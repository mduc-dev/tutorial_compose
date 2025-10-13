package com.compose.taptap.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry

public val LocalComposeNavigator: ProvidableCompositionLocal<AppComposeNavigator<TapTapScreen>> =
    compositionLocalOf {
        error(
            "No AppComposeNavigator provided! " +
                    "Make sure to wrap all usages of TapTap components in TapTapTheme.",
        )
    }

/**
 * Retrieves the current [AppComposeNavigator] at the call site's position in the hierarchy.
 */
public val currentComposeNavigator: AppComposeNavigator<TapTapScreen>
    @Composable
    @ReadOnlyComposable
    get() = LocalComposeNavigator.current


/**
 * Lifecycle is resumed i.e not yet destroyed.
 */
internal val NavBackStackEntry.lifecycleIsResumed: Boolean
    get() = lifecycle.currentState == Lifecycle.State.RESUMED