package com.compose.taptap.domain.repositories

import com.compose.taptap.domain.models.GameDetails
import kotlinx.coroutines.flow.Flow

interface GameDetailsRepository {
    /** Fetch Game Details */
    suspend fun fetchGameDetails(
        gameId: Int
    ): Result<Flow<GameDetails>>
}