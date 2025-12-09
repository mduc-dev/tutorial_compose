package com.compose.taptap.core.designsystem.util

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.navigation.TapTapScreen


fun isEmpty(value: Any?): Boolean {
    val identifier = "[IsEmpty Checking]"
    Log.i(
        identifier,
        "${DebuggingIdentifiers.actionOrEventSucceded} value: $value is empty",
    )
    if (value == null) return true
    return when (value) {
        is String -> value.isEmpty()
        is Collection<*> -> value.isEmpty()
        else -> false
    }
}


val BOTTOM_TAB: List<BottomTab>
    @Composable get() = listOf(
        BottomTab(
            title = "Games",
            icon = painterResource(id = R.drawable.cw_home_bottom_games_icon_unselect),
            selectedIcon = painterResource(id = R.drawable.cw_home_bottom_games_icon_selected),
            route = TapTapScreen.Game
        ),
        BottomTab(
            title = "Play",
            icon = painterResource(id = R.drawable.intl_cc_24_bottom_bar_games_unselect),
            selectedIcon = painterResource(id = R.drawable.intl_cc_24_bottom_bar_games_select),
            route = TapTapScreen.Play
        ),
        BottomTab(
            title = "Tavern",
            icon = painterResource(id = R.drawable.home_bottom_icon_tavern_unselect),
            selectedIcon = painterResource(id = R.drawable.home_bottom_icon_tavern_selected),
            route = TapTapScreen.Tavern
        ),
        BottomTab(
            title = "Me",
            icon = rememberVectorPainter(Icons.Outlined.AccountCircle),
            selectedIcon = rememberVectorPainter(Icons.Rounded.AccountCircle),
            route = TapTapScreen.Me
        ),
    )
