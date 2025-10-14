package com.compose.taptap.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.ui.theme.BlackF16
import com.compose.taptap.ui.theme.Kotlin_composeTheme
import com.compose.taptap.ui.utils.BOTTOM_TAB
import com.compose.taptap.ui.utils.DisabledInteractionSource

@Composable
fun TapBottomTab(
    modifier: Modifier,
    currentRoute: TapTapScreen?,
    onItemClick: (TapTapScreen) -> Unit,
) {
    val navBarColors = NavigationBarItemDefaults.colors(
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = White,
        unselectedTextColor = Gray,
        indicatorColor = Transparent,
    )

    NavigationBar(modifier = modifier, containerColor = BlackF16) {
        BOTTOM_TAB.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                interactionSource = DisabledInteractionSource(),
                onClick = { onItemClick(item.route) },
                icon = {
                    Icon(
                        painter = if (currentRoute == item.route) item.selectedIcon else item.icon,
                        contentDescription = item.title,
                        tint = if (item.title == "You") White else Unspecified
                    )
                },
                label = { Text(item.title, color = White) },
                colors = navBarColors,
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BottomTabNavigationPreview() {
    Kotlin_composeTheme(dynamicColor = false) {
        TapBottomTab(currentRoute = TapTapScreen.Game, modifier = Modifier, onItemClick = {
            println("vao day")
        })
    }
}