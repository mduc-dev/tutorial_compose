package com.compose.taptap.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.ui.launcher.account.Account
import com.compose.taptap.ui.launcher.forgotpassword.ForgotPassword
import com.compose.taptap.ui.launcher.game.Game
import com.compose.taptap.ui.launcher.game_detail.GameDetail
import com.compose.taptap.ui.launcher.login.Login
import com.compose.taptap.ui.launcher.login_without_password.LoginWithoutPassword
import com.compose.taptap.ui.launcher.notifications.Notifications
import com.compose.taptap.ui.launcher.play.Play
import com.compose.taptap.ui.launcher.search.Search
import com.compose.taptap.ui.launcher.signup.Signup
import com.compose.taptap.ui.launcher.tavern.Tavern
import com.compose.taptap.ui.launcher.welcome.Welcome

fun NavGraphBuilder.tapAuthNavigation(
) {
    navigation<TapTapScreen.AuthGraph>(
        startDestination = TapTapScreen.Welcome
    ) {
        composable<TapTapScreen.Welcome> {
            Welcome()
        }
        composable<TapTapScreen.Login>(enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(300)
            )
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(300)
            )
        }, popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(300)
            )
        }, popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(300)
            )
        }) {
            Login()
        }
        composable<TapTapScreen.SignUp> {
            Signup()
        }
        composable<TapTapScreen.ForgotPassword> {
            ForgotPassword()
        }
        composable<TapTapScreen.LoginWithoutPassword> {
            LoginWithoutPassword()
        }
    }
}

fun NavGraphBuilder.tapMainNavigation() {
    navigation<TapTapScreen.MainGraph>(
        startDestination = TapTapScreen.Game
    ) {
        composable<TapTapScreen.Game> { Game() }
        composable<TapTapScreen.Play> { Play() }
        composable<TapTapScreen.Tavern> { Tavern() }
        composable<TapTapScreen.You> { Account() }

        composable<TapTapScreen.Search> {
            Search()
        }
        composable<TapTapScreen.Notifications> {
            Notifications()
        }
        composable<TapTapScreen.GameDetail> {
            GameDetail()
        }
    }
}
