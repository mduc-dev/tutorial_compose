package com.compose.taptap.core.navigation

import kotlinx.serialization.Serializable

sealed interface TapTapScreen {
    data object AuthGraph : TapTapScreen
    data object MainGraph : TapTapScreen

    //auth screens
    data object Welcome : TapTapScreen
    data object Login : TapTapScreen
    data object SignUp : TapTapScreen
    data object ForgotPassword : TapTapScreen
    data object LoginWithoutPassword : TapTapScreen

    //main screens
    data object MainScaffold : TapTapScreen
    @Serializable
    data object Game : TapTapScreen
    data object Play : TapTapScreen
    data object Tavern : TapTapScreen
    data object You : TapTapScreen


    //common screens
    data object Search : TapTapScreen
    data object GameDetail : TapTapScreen

    data object Notifications : TapTapScreen
}