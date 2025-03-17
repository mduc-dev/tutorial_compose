package com.example.kotlin_compose.presentation.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlin_compose.R
import com.example.kotlin_compose.ui.theme.Kotlin_composeTheme
import com.example.kotlin_compose.presentation.utils.DisabledInteractionSource


@Composable
fun BottomTabNavigator(
    selectedItem: Int,
    onItemClick: (Int) -> Unit,
) {
    NavigationBar(containerColor = Color.Black) {
        Row {
            BOTTOM_TAB.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItem == index,
                    interactionSource = DisabledInteractionSource(),
                    onClick = {
                        onItemClick(index)
                    },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (item.badgeCount != null) {
                                    Badge {
                                        Text(text = item.badgeCount.toString())
                                    }
                                } else if (item.hasBadge == true) {
                                    Badge()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = Color.White,
                            )
                        }
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = Color.White
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = colorResource(id = R.color.white),
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent,

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