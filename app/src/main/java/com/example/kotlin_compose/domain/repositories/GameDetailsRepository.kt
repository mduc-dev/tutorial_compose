package com.example.kotlin_compose.domain.repositories

import androidx.paging.PagingData
import com.example.kotlin_compose.domain.models.GameDetails
import com.example.kotlin_compose.domain.models.Games
import kotlinx.coroutines.flow.Flow

interface GameDetailsRepository {
    /** Fetch Game Details */
    suspend fun fetchGameDetails(
        gameId: Int
    ): Result<Flow<GameDetails>>
}