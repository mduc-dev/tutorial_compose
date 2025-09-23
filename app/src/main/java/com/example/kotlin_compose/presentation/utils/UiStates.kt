package com.example.kotlin_compose.presentation.utils

import androidx.paging.PagingData
import com.example.kotlin_compose.domain.models.Games
import kotlinx.coroutines.flow.Flow


sealed class AuthState {
    object Idle : AuthState()
    data class Loading(val provider: Provider? = null) : AuthState()
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}


data class GameUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val trendingGames: Flow<PagingData<Games>>? = null,
    val popularGames: Flow<PagingData<Games>>? = null,
    val upcomingGames: Flow<PagingData<Games>>? = null
)


enum class Provider { Facebook, Google }
