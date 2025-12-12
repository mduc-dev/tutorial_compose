package com.compose.taptap.core.navigation

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
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
    data object Me : TapTapScreen

    @Serializable
    data object Badge : TapTapScreen


    //common screens
    @Serializable
    data object Search : TapTapScreen

    @Serializable
    data object GameDetail : TapTapScreen

    @Serializable
    data object Notifications : TapTapScreen

    @Serializable
    data object Settings : TapTapScreen

    @Serializable
    data object InAppUpdate : TapTapScreen

    @Serializable
    data object GameUpdate : TapTapScreen

    // Settings Screens
    @Serializable
    data object Feedback : TapTapScreen

    @Serializable
    data object AccountSecurity : TapTapScreen

    @Serializable
    data object DarkMode : TapTapScreen

    @Serializable
    data object Languages : TapTapScreen

    @Serializable
    data object OrdersPayments : TapTapScreen

    @Serializable
    data object General : TapTapScreen

    @Serializable
    data object Redeem : TapTapScreen

    @Serializable
    data object DownloadInstall : TapTapScreen

    @Serializable
    data object NotificationSettings : TapTapScreen

    @Serializable
    data object AboutTapTap : TapTapScreen

    @Serializable
    data object TermsOfService : TapTapScreen

    @Serializable
    data object Privacy : TapTapScreen

    @Serializable
    data object PrivacyPolicy : TapTapScreen

    @Serializable
    data object Authorization : TapTapScreen
}
