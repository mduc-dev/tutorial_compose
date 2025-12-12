package com.compose.taptap.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

val LocalComposeNavigator: ProvidableCompositionLocal<TapTapNavigator> =
    compositionLocalOf {
        error(
            "No TapTapNavigator provided! " +
                    "Make sure to wrap all usages of TapTap components in TapTapTheme.",
        )
    }

/**
 * Retrieves the current [TapTapNavigator] at the call site's position in the hierarchy.
 */
val currentComposeNavigator: TapTapNavigator
    @Composable
    @ReadOnlyComposable
    get() = LocalComposeNavigator.current
