package com.compose.taptap.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NamedNavArgument
import com.compose.taptap.data.source.local.BottomNavigation
import com.taptap.R


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
    data object MainScaffold : TapTapScreens("main_scaffold")
    data object Game : TapTapScreens("game")
    data object Play : TapTapScreens("play")
    data object Tavern : TapTapScreens("tavern")
    data object You : TapTapScreens("you")


    //common screens
    data object Search : TapTapScreens("search")
    data object GameDetail : TapTapScreens("gameDetail/{gameId}")

    data object Notifications : TapTapScreens("notifications")
}


val BOTTOM_TAB: List<BottomNavigation>
    @Composable get() = listOf(
        BottomNavigation(
            title = "Games",
            icon = painterResource(id = R.drawable.cw_home_bottom_games_icon_unselect),
            selectedIcon = painterResource(id = R.drawable.cw_home_bottom_games_icon_selected),
            route = TapTapScreens.Game.route
        ),
        BottomNavigation(
            title = "Play",
            icon = painterResource(id = R.drawable.intl_cc_24_bottom_bar_games_unselect),
            selectedIcon = painterResource(id = R.drawable.intl_cc_24_bottom_bar_games_select),
            route = TapTapScreens.Play.route
        ),
        BottomNavigation(
            title = "Tavern",
            icon = painterResource(id = R.drawable.home_bottom_icon_tavern_unselect),
            selectedIcon = painterResource(id = R.drawable.home_bottom_icon_tavern_selected),
            route = TapTapScreens.Tavern.route,
            hasBadge = true,
            badgeCount = 10
        ),
        BottomNavigation(
            title = "You",
            icon = rememberVectorPainter(Icons.Outlined.AccountCircle),
            selectedIcon = rememberVectorPainter(Icons.Rounded.AccountCircle),
            route = TapTapScreens.You.route
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