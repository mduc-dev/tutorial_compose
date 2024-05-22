package com.example.kotlin_compose.presentation.screens.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kotlin_compose.presentation.components.CustomButton
import com.example.kotlin_compose.presentation.components.NoExistData
import com.example.kotlin_compose.presentation.utils.isEmpty
import com.example.kotlin_compose.ui.theme.PPNeu

@Composable
fun Account() {
    val styleTextBtn: TextStyle =
        MaterialTheme.typography.titleMedium.copy(
            color = Color.White,
            fontFamily = PPNeu,
            fontWeight = FontWeight.Bold
        )
    val listButton = listOf("All", "Gamelists", "Articles", "Videos")

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isEmpty("hello")) {
                NoExistData(subTextNull = "Write a post to start your profile’s never-ending journey.")
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(listButton.size) {
                        CustomButton(
                            onPress = {},
                            enabled = false,
                            children = {
                                Text(
                                    listButton[it],
                                    color = Color.White,
                                    style = styleTextBtn
                                )
                            },
                        )
                    }
                }

//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    CustomButton(
//                        onPress = {},
//                        enabled = false,
//                        children = {
//                            Text(
//                                "All",
//                                color = Color.White,
//                                style = styleTextBtn
//                            )
//                        },
//                    )
//                    CustomButton(
//                        onPress = {},
//                        enabled = false,
//                        children = {
//                            Text(
//                                "Gamelists",
//                                color = Color.White,
//                                style = styleTextBtn
//                            )
//                        },
//                    )
//                    CustomButton(
//                        onPress = {},
//                        enabled = false,
//                        children = {
//                            Text(
//                                "Articles",
//                                color = Color.White,
//                                style = styleTextBtn
//                            )
//                        },
//                    )
//                    CustomButton(
//                        onPress = {},
//                        enabled = false,
//                        children = {
//                            Text(
//                                "Videos",
//                                color = Color.White,
//                                style = styleTextBtn
//                            )
//                        },
//                    )
//                }
            }

        }
    }
}


@Preview(apiLevel = 33, showBackground = true, backgroundColor = 0xFF1A1A1A, showSystemUi = true)
@Composable
fun AccountPreview() {
    Account()
}