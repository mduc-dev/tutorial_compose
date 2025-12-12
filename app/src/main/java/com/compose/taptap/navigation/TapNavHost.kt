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
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.compose.taptap.core.designsystem.theme.TapTapDimens
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.navigation.LocalComposeNavigator
import com.compose.taptap.core.navigation.TapTapNavigatorImpl
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.feature.auth.AuthState
import com.compose.taptap.feature.auth.welcome.LocalWelcomeViewModel
import com.compose.taptap.feature.auth.welcome.WelcomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TapNavHost() {
    val welcomeViewModel: WelcomeViewModel = koinViewModel()
    val authState = welcomeViewModel.uiState.collectAsStateWithLifecycle().value

    CompositionLocalProvider(LocalWelcomeViewModel provides welcomeViewModel) {
        when (authState) {
            is AuthState.Authenticated -> TapMainNavigationHost()
            is AuthState.Unauthenticated, is AuthState.Idle, is AuthState.Error, is AuthState.Loading -> {
                TapAuthNavigationHost()
            }
        }
    }
}

@Composable
private fun TapAuthNavigationHost() {
    val backStack = rememberNavBackStack(TapTapScreen.Welcome)
    val navigator = remember(backStack) { TapTapNavigatorImpl(backStack) }

    CompositionLocalProvider(LocalComposeNavigator provides navigator) {
        NavDisplay(
            backStack = backStack,
            entryProvider = tapAuthEntryProvider,
            modifier = Modifier
                .background(TapTapTheme.colors.scrim)
                .fillMaxSize()
        )
    }
}

@Composable
private fun TapMainNavigationHost() {
    val (isFlipped, setIsFlipped) = remember { mutableStateOf(false) }
    val (flipBackImageUrl, setFlipBackImageUrl) = remember { mutableStateOf<String?>(null) }
    
    val backStack = rememberNavBackStack(TapTapScreen.Game)
    val navigator = remember(backStack) { TapTapNavigatorImpl(backStack) }
    
    val currentScreen = backStack.lastOrNull() as? TapTapScreen

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

    CompositionLocalProvider(LocalComposeNavigator provides navigator) {
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
                            navigator.navigate(screen)
                        }
                    })
            }, content = { _ ->
                NavDisplay(
                    backStack = backStack,
                    entryProvider = tapMainEntryProvider(
                        onToggleFlip = { shouldFlip, imageUrl ->
                            if (imageUrl != null) setFlipBackImageUrl(imageUrl)
                            setIsFlipped(shouldFlip)
                        }),
                    modifier = Modifier.fillMaxSize()
                )
            })
    }
}
