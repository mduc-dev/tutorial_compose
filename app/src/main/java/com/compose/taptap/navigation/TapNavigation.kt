package com.compose.taptap.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.compose.taptap.ui.launcher.forgotpassword.ForgotPassword
import com.compose.taptap.ui.launcher.game_detail.GameDetail
import com.compose.taptap.ui.launcher.login.Login
import com.compose.taptap.ui.launcher.login_without_password.LoginWithoutPassword
import com.compose.taptap.ui.launcher.notifications.Notifications
import com.compose.taptap.ui.launcher.search.Search
import com.compose.taptap.ui.launcher.signup.Signup
import com.compose.taptap.ui.launcher.welcome.Welcome

fun NavGraphBuilder.tapAuthNavigation(
) {
    navigation(
        route = TapTapScreens.AuthGraph.route, startDestination = TapTapScreens.Welcome.route
    ) {
        composable(route = TapTapScreens.Welcome.route) {
            Welcome()
        }
        composable(route = TapTapScreens.Login.route, enterTransition = {
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
        composable(route = TapTapScreens.SignUp.route) {
            Signup()
        }
        composable(route = TapTapScreens.ForgotPassword.route) {
            ForgotPassword()
        }
        composable(route = TapTapScreens.LoginWithoutPassword.route) {
            LoginWithoutPassword()
        }
    }
}

fun NavGraphBuilder.tapMainNavigation() {
    navigation(
        route = TapTapScreens.MainGraph.route, startDestination = TapTapScreens.MainScaffold.route
    ) {
        composable(TapTapScreens.MainScaffold.route) {
            MainNavGraph()
        }
        composable(TapTapScreens.Search.route) {
            Search()
        }
        composable(TapTapScreens.Notifications.route) {
            Notifications()
        }
        composable(TapTapScreens.GameDetail.route) {
            GameDetail()
        }
    }
}
