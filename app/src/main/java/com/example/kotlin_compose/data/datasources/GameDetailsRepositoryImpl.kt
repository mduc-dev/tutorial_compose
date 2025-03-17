package com.example.kotlin_compose.data.datasources

import com.example.kotlin_compose.domain.models.GameDetails
import com.example.kotlin_compose.domain.repositories.GameDetailsRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow

class GameDetailsRepositoryImpl(private val httpClient: HttpClient) : GameDetailsRepository {
    override suspend fun fetchGameDetails(gameId: Int): Result<Flow<GameDetails>> {
        TODO("Not yet implemented")
    }
}