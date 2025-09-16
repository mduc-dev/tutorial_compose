package com.example.kotlin_compose.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NamedNavArgument
import com.example.kotlin_compose.R
import com.example.kotlin_compose.data.local.BottomNavigation


sealed class TapTapScreens(
    val route: String, val navArgument: List<NamedNavArgument> = emptyList()
) {
    val name: String = route.appendArguments(navArgument)

    //Graph
    data object AuthGraph : TapTapScreens("auth_graph")
    data object MainGraph : TapTapScreens("main_graph")

    //auth screens
    data object Welcome : TapTapScreens("welcome")
    data object Login : TapTapScreens("login")
    data object SignUp : TapTapScreens("signup")
    data object ForgotPassword : TapTapScreens("forgot_password")
    data object LoginWithoutPassword : TapTapScreens("login_without_password")

    //main screens
    data object Home : TapTapScreens("home")
    data object Play : TapTapScreens("play")
    data object Tavern : TapTapScreens("tavern")
    data object You : TapTapScreens("you")
}


val BOTTOM_TAB: List<BottomNavigation>
    @Composable get() = listOf(
        BottomNavigation(
            title = "Games",
            icon = ImageVector.vectorResource(id = R.drawable.cw_home_bottom_games_icon_unselect),
            route = TapTapScreens.Home.route
        ),
        BottomNavigation(
            title = "Play",
            icon = ImageVector.vectorResource(id = R.drawable.play_icon),
            route = TapTapScreens.Play.route
        ),
        BottomNavigation(
            title = "Tavern",
            icon = ImageVector.vectorResource(id = R.drawable.home_bottom_icon_tavern_unselect),
            route = TapTapScreens.Tavern.route,
            hasBadge = true,
            badgeCount = 10
        ),
        BottomNavigation(
            title = "You", icon = Icons.Rounded.AccountCircle, route = TapTapScreens.You.route
        ),
    )

private fun String.appendArguments(navArguments: List<NamedNavArgument>): String {
    val mandatoryArguments =
        navArguments.filter { it.argument.defaultValue == null }.takeIf { it.isNotEmpty() }
            ?.joinToString(separator = "/", prefix = "/") { "{${it.name}}" }.orEmpty()
    val optionalArguments =
        navArguments.filter { it.argument.defaultValue != null }.takeIf { it.isNotEmpty() }
            ?.joinToString(separator = "&", prefix = "?") { "${it.name}={${it.name}}" }.orEmpty()
    return "$this$mandatoryArguments$optionalArguments"
}