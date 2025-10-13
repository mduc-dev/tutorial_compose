package com.compose.taptap.core.navigation

import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions

class TapComposeNavigator : AppComposeNavigator<TapTapScreen>() {
//    override fun navigate(route: TapTapScreen, optionsBuilder: (NavOptionsBuilder.() -> Unit)?) {
//        val navController = navControllerFlow.value
//        if (navController != null) {
//            val currentEntry = navController.currentBackStackEntry
//
//            // Jetpack Navigation only allows executing a new navigation action when the
//            // current destination is in the RESUMED state. Dropping commands while a
//            // transition is still in progress prevents the flicker observed when the user
//            // quickly taps navigate/back in succession.
//            if (currentEntry != null && !currentEntry.lifecycleIsResumed) {
//                return
//            }
//
//            // Avoid queuing up duplicate navigation commands when the destination is already
//            // visible. This prevents quick successive taps from triggering transient flashes
//            // caused by re-navigating to the same screen.
//            if (navController.currentDestination?.route == route) {
//                return
//            }
//        }
//
//        val options = optionsBuilder?.let { navOptions(it) }
//        navigationCommands.tryEmit(ComposeNavigationCommand.NavigateToRoute(route, options))
//    }

    override fun navigate(route: TapTapScreen, optionsBuilder: (NavOptionsBuilder.() -> Unit)?) {
        val options = optionsBuilder?.let { navOptions(it) }
        navigationCommands.tryEmit(ComposeNavigationCommand.NavigateToRoute(route, options))
    }

    override fun navigateAndClearBackStack(route: TapTapScreen) {
        navigationCommands.tryEmit(
            ComposeNavigationCommand.NavigateToRoute(
                route, navOptions {
                    popUpTo(0)
                })
        )
    }

    override fun popUpTo(route: TapTapScreen, inclusive: Boolean) {
        navigationCommands.tryEmit(ComposeNavigationCommand.PopUpToRoute(route, inclusive))
    }

    override fun <R> navigateBackWithResult(key: String, result: R, route: TapTapScreen?) {
        navigationCommands.tryEmit(
            ComposeNavigationCommand.NavigateUpWithResult(
                key = key,
                result = result,
                route = route,
            ),
        )
    }
}