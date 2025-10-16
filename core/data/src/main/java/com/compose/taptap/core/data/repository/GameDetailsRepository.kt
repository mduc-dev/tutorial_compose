package com.compose.taptap.core.data.repository

import kotlinx.coroutines.flow.Flow

interface GameDetailsRepository {
    /** Fetch Game Details */
    suspend fun fetchGameDetails(
        gameId: Int
    ): Result<Flow<GameDetails>>
}