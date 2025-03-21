package com.example.kotlin_compose.domain.repositories

import app.cash.paging.PagingData
import com.example.kotlin_compose.domain.models.Games
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    /** Fetch Trending games from data source*/
    suspend fun fetchTrendingGames(): Result<Flow<PagingData<Games>>>

    /** Fetch Popular games from data source*/
    suspend fun fetchPopularGames(): Result<Flow<PagingData<Games>>>

    /** Fetch Upcoming games from data source*/
    suspend fun fetchUpcomingGames(): Result<Flow<PagingData<Games>>>
}
