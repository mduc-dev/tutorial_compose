package com.compose.taptap.core.viewmodel

import androidx.paging.PagingData
import com.compose.taptap.core.model.Games
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class GameUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val trendingGames: Flow<PagingData<Games>> = emptyFlow(),
    val popularGames: Flow<PagingData<Games>> = emptyFlow(),
    val upcomingGames: Flow<PagingData<Games>> = emptyFlow()
)
