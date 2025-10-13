package com.compose.taptap.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NamedNavArgument
import com.compose.taptap.R
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.data.source.local.BottomNavigation



val BOTTOM_TAB: List<BottomNavigation>
    @Composable get() = listOf(
        BottomNavigation(
            title = "Games",
            icon = painterResource(id = R.drawable.cw_home_bottom_games_icon_unselect),
            selectedIcon = painterResource(id = R.drawable.cw_home_bottom_games_icon_selected),
            route = TapTapScreen.Game
        ),
        BottomNavigation(
            title = "Play",
            icon = painterResource(id = R.drawable.intl_cc_24_bottom_bar_games_unselect),
            selectedIcon = painterResource(id = R.drawable.intl_cc_24_bottom_bar_games_select),
            route = TapTapScreen.Play
        ),
        BottomNavigation(
            title = "Tavern",
            icon = painterResource(id = R.drawable.home_bottom_icon_tavern_unselect),
            selectedIcon = painterResource(id = R.drawable.home_bottom_icon_tavern_selected),
            route = TapTapScreen.Tavern,
            hasBadge = true,
            badgeCount = 10
        ),
        BottomNavigation(
            title = "You",
            icon = rememberVectorPainter(Icons.Outlined.AccountCircle),
            selectedIcon = rememberVectorPainter(Icons.Rounded.AccountCircle),
            route = TapTapScreen.You
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