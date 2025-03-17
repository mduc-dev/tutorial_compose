package com.example.kotlin_compose.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kotlin_compose.R
import com.example.kotlin_compose.data.local.BottomNavigation
import androidx.compose.ui.res.vectorResource

object Route {
    // Auth routes
    const val INTRO = "intro"
    const val LOGIN = "login"
    const val SIGNUP = "signup"

    // Main routes
    const val GAMES = "games"
    const val PLAY = "play"
    const val TAVERN = "tavern"
    const val YOU = "account"
}


val BOTTOM_TAB: List<BottomNavigation>
    @Composable
    get() = listOf(
        BottomNavigation(
            title = "Games",
            icon = ImageVector.vectorResource(id = R.drawable.cw_home_bottom_games_icon_unselect),
            route = Route.GAMES
        ),
        BottomNavigation(
            title = "Play",
            icon = ImageVector.vectorResource(id = R.drawable.play_icon),
            route = Route.PLAY
        ),
        BottomNavigation(
            title = "Tavern",
            icon = ImageVector.vectorResource(id = R.drawable.home_bottom_icon_tavern_unselect),
            route = Route.TAVERN,
            hasBadge = true,
            badgeCount = 10
        ),
        BottomNavigation(
            title = "You",
            icon = Icons.Rounded.AccountCircle,
            route = Route.YOU
        ),
    )