package com.compose.taptap.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.compose.taptap.core.designsystem.theme.TapTapDimens
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.feature.auth.AuthState
import com.compose.taptap.feature.auth.welcome.LocalWelcomeViewModel
import com.compose.taptap.feature.auth.welcome.WelcomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TapNavHost(
    navHostController: NavHostController
) {
    val welcomeViewModel: WelcomeViewModel = koinViewModel()
    val authState = welcomeViewModel.uiState.collectAsStateWithLifecycle().value

    CompositionLocalProvider(LocalWelcomeViewModel provides welcomeViewModel) {
        when (authState) {
            is AuthState.Authenticated -> TapMainNavigationHost(navHostController)

            is AuthState.Unauthenticated, is AuthState.Idle, is AuthState.Error, is AuthState.Loading -> {
                TapAuthNavigationHost(navHostController)
            }
        }
    }
}

@Composable
private fun TapAuthNavigationHost(navHostController: NavHostController) {
    NavHost(
        navHostController,
        startDestination = TapTapScreen.AuthGraph,
        modifier = Modifier
            .background(TapTapTheme.colors.scrim)
            .fillMaxSize()
    ) {
        tapAuthNavigation()
    }
}

@Composable
private fun TapMainNavigationHost(navHostController: NavHostController) {
    val (isFlipped, setIsFlipped) = remember { mutableStateOf(false) }
    val (flipBackImageUrl, setFlipBackImageUrl) = remember { mutableStateOf<String?>(null) }

    val currentRoute =
        navHostController.currentBackStackEntryAsState().value?.destination?.route?.substringBefore(
            '?'
        )
    val currentScreen = when (currentRoute) {
        TapTapScreen.Game::class.qualifiedName -> TapTapScreen.Game
        TapTapScreen.Play::class.qualifiedName -> TapTapScreen.Play
        TapTapScreen.Tavern::class.qualifiedName -> TapTapScreen.Tavern
        TapTapScreen.Me::class.qualifiedName -> TapTapScreen.Me
        TapTapScreen.Search::class.qualifiedName -> TapTapScreen.Search
        TapTapScreen.Notifications::class.qualifiedName -> TapTapScreen.Notifications
        TapTapScreen.GameDetail::class.qualifiedName -> TapTapScreen.GameDetail
        TapTapScreen.Settings::class.qualifiedName -> TapTapScreen.Settings
        TapTapScreen.InAppUpdate::class.qualifiedName -> TapTapScreen.InAppUpdate
        else -> null
    }

    val bottomBarHeightPx =
        with(LocalDensity.current) { TapTapDimens.BottomBarHeight.roundToPx().toFloat() }

    val isBottomBarVisible = currentScreen != null && currentScreen.isTabItem()

    val bottomBarOffsetY by animateFloatAsState(
        targetValue = if (isBottomBarVisible) 0f else bottomBarHeightPx,
        animationSpec = tween(durationMillis = 300),
        label = "BottomBarOffset"
    )
    val bottomBarAlpha by animateFloatAsState(
        targetValue = if (isBottomBarVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "BottomBarAlpha"
    )

    Scaffold(
        bottomBar = {
            TapBottomTab(
                modifier = Modifier.graphicsLayer {
                    translationY = bottomBarOffsetY
                    alpha = bottomBarAlpha
                    compositingStrategy = CompositingStrategy.ModulateAlpha
                },
                currentRoute = currentScreen,
                isFlipped = isFlipped,
                flipBackImageUrl = flipBackImageUrl,
                onFlip = {
                    // TODO: Implement flip click logic, e.g., trigger random play.
                },
                onItemClick = { screen ->
                    if (screen != currentScreen) {
                        navHostController.navigate(screen) {
                            popUpTo(navHostController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                })
        }, content = { _ ->
            NavHost(
                navHostController,
                startDestination = TapTapScreen.MainGraph,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                tapMainNavigation(
                    onToggleFlip = { shouldFlip, imageUrl ->
                        if (imageUrl != null) setFlipBackImageUrl(imageUrl)
                        setIsFlipped(shouldFlip)
                    })
            }
        })
}
