package com.example.kotlin_compose.presentation.screens.home

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.cash.paging.compose.collectAsLazyPagingItems
import com.example.kotlin_compose.presentation.components.AppBar
import com.example.kotlin_compose.presentation.components.GameCardPortraitCompact
import com.example.kotlin_compose.presentation.components.NoExistData
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun Home(viewModel: HomeViewModel = koinViewModel<HomeViewModel>()) {
    val scrollState = rememberScrollState()
    val homeUiState = viewModel.homeUiState.collectAsState().value

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .padding(PaddingValues()),
        topBar = { AppBar(title = "Home") }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (homeUiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (!homeUiState.error.isNullOrEmpty()) {
                NoExistData(subTextNull = "${homeUiState.error}", modifier = Modifier)
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    homeUiState.trendingGames?.let { trendingGames ->
                        val trendingGamesVal = trendingGames.collectAsLazyPagingItems()

                        LazyRow {
                            items(trendingGamesVal.itemCount) { index ->
                                trendingGamesVal[index]?.let {
                                    GameCardPortraitCompact(game = it, onItemClick = { trending ->

                                    })
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}