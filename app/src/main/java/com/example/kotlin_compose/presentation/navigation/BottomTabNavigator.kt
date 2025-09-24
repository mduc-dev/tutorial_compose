package com.example.kotlin_compose.presentation.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Row
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
    selectedItem: Int,
    onItemClick: (Int) -> Unit,
) {
    NavigationBar(containerColor = BlackF16) {
        Row {
            BOTTOM_TAB.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItem == index,
                    interactionSource = DisabledInteractionSource(),
                    onClick = {
                        onItemClick(index)
                    },
                    icon = {
                        val painter = if (selectedItem == index) item.selectedIcon else item.icon

                        Icon(
                            painter = painter,
                            contentDescription = item.title,
                            tint = if (item.title == "You") {
                                if (selectedItem == index) White else White
                            } else
                                Unspecified

                        )
                    },
                    label = {
                        Text(
                            text = item.title, color = White
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = White,
                        unselectedTextColor = Gray,
                        indicatorColor = Transparent,
                    ),
                )
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BottomTabNavigationPreview() {
    Kotlin_composeTheme(dynamicColor = false) {
        BottomTabNavigator(selectedItem = 0, onItemClick = {
            println("vao day")
        })
    }
}