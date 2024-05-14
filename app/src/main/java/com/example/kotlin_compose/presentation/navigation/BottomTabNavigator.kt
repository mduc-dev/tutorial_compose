package com.example.kotlin_compose.presentation.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlin_compose.R
import com.example.kotlin_compose.ui.theme.Kotlin_composeTheme

@Composable
fun BottomTabNavigator(
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {
    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface),
        ) {
            BOTTOM_TAB.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItem == index,

                    onClick = {
                        onItemClick(index)
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = colorResource(id = R.color.body),
                        unselectedTextColor = colorResource(id = R.color.body),
                        indicatorColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    }
}
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BottomTabNavigationPreview() {
    Kotlin_composeTheme(dynamicColor = false) {
        BottomTabNavigator(selectedItem = 0, onItemClick = {
            println("vao day")
        })
    }
}