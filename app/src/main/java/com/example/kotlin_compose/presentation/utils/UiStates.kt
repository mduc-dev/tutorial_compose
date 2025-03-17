package com.example.kotlin_compose.presentation.utils

import androidx.paging.PagingData
import com.example.kotlin_compose.domain.models.Games
import kotlinx.coroutines.flow.Flow


sealed class AuthState {
    data object Loading : AuthState()
    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}


data class HomeUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val trendingGames: Flow<PagingData<Games>>? = null,
    val popularGames: Flow<PagingData<Games>>? = null,
    val upcomingGames: Flow<PagingData<Games>>? = null
)