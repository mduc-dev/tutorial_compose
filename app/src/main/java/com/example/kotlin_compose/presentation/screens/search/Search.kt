package com.example.kotlin_compose.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kotlin_compose.R
import com.example.kotlin_compose.presentation.navigation.AppComposeNavigator
import org.koin.androidx.compose.koinViewModel
import com.example.kotlin_compose.ui.theme.*

@Composable
fun Search(
    composeNavigator: AppComposeNavigator,
    searchViewModel: SearchViewModel = koinViewModel<SearchViewModel>()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IntlV2Black)
            .statusBarsPadding()
    ) {
        Text("Search")
    }
}
