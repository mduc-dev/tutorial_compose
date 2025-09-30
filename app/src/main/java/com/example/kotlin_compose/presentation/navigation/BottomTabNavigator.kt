package com.example.kotlin_compose.presentation.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlin_compose.presentation.utils.DisabledInteractionSource
import com.example.kotlin_compose.ui.theme.BlackF16
import com.example.kotlin_compose.ui.theme.Kotlin_composeTheme


@Composable
fun BottomTabNavigator(
    currentRoute: String?,
    onItemClick: (String) -> Unit,
) {
    val navBarColors = NavigationBarItemDefaults.colors(
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = White,
        unselectedTextColor = Gray,
        indicatorColor = Transparent,
    )

    NavigationBar(containerColor = BlackF16) {
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
        BottomTabNavigator(currentRoute = "Games", onItemClick = {
            println("vao day")
        })
    }
}