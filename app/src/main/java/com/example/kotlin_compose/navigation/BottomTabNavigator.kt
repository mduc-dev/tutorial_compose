package com.example.kotlin_compose.navigation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_compose.data.BottomNavigation

val items =
    listOf(
        BottomNavigation(
            title = "Home",
            icon = Icons.Rounded.Home,
        ),
        BottomNavigation(
            title = "Notifications",
            icon = Icons.Rounded.Notifications,
        ),
        BottomNavigation(
            title = "Account",
            icon = Icons.Rounded.AccountCircle,
        ),
    )

@Preview
@Composable
fun BottomTabNavigator() {
    val navController = rememberNavController()
    val selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface),
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItem == index,

                    onClick = {
                        navController.navigate(item.title)
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
                )
            }
        }
    }
}
