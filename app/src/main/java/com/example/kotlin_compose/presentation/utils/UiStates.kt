package com.example.kotlin_compose.presentation.utils

import InstantGameItem
import androidx.paging.PagingData
import com.example.kotlin_compose.data.network.utils.ApiResult
import com.example.kotlin_compose.domain.models.Games
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


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
    val trendingGames: Flow<PagingData<Games>> = emptyFlow(),
    val popularGames: Flow<PagingData<Games>> = emptyFlow(),
    val upcomingGames: Flow<PagingData<Games>> = emptyFlow()
)


data class PLayUiState(
    val games: Flow<PagingData<InstantGameItem>> = emptyFlow(),
    val recently: ApiResult<Any> = ApiResult.Loading
)


enum class Provider { Facebook, Google }
