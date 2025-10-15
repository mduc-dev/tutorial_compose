package com.compose.taptap.core.navigation

import kotlinx.serialization.Serializable

sealed interface TapTapScreen {
    @Serializable data object AuthGraph : TapTapScreen
    @Serializable data object MainGraph : TapTapScreen

    //auth screens
    @Serializable
    data object Welcome : TapTapScreen

    @Serializable
    data object Login : TapTapScreen

    @Serializable
    data object SignUp : TapTapScreen

    @Serializable
    data object ForgotPassword : TapTapScreen

    @Serializable
    data object LoginWithoutPassword : TapTapScreen

    //bottom tab screens
    @Serializable
    data object Game : TapTapScreen

    @Serializable
    data object Play : TapTapScreen

    @Serializable
    data object Tavern : TapTapScreen

    @Serializable
    data object You : TapTapScreen


    //common screens
    @Serializable
    data object Search : TapTapScreen

    @Serializable
    data object GameDetail : TapTapScreen

    @Serializable
    data object Notifications : TapTapScreen
}
