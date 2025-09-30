package com.example.kotlin_compose.data.source.remote

import com.example.kotlin_compose.data.network.models.GamesDto
import com.example.kotlin_compose.domain.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GamesRemoteDataSource {
    suspend fun fetchGames(): Result<GamesDto>
}

class KtorGamesRemoteDataSource(
    private val httpClient: HttpClient,
    private val endpointProvider: () -> String = { Constants.gameUrl() }
) : GamesRemoteDataSource {
    override suspend fun fetchGames(): Result<GamesDto> = runCatching {
        withContext(Dispatchers.IO) {
            httpClient.get(endpointProvider())
        }.body()
    }
}
