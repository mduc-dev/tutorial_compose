package com.example.kotlin_compose.data.datasources

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.example.kotlin_compose.data.mappers.toGames
import com.example.kotlin_compose.data.network.models.GamesDto
import com.example.kotlin_compose.data.paging.BasePagingSource
import com.example.kotlin_compose.domain.models.Games
import com.example.kotlin_compose.domain.repositories.GamesRepository
import com.example.kotlin_compose.domain.utils.Constants.GAME_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow

class GamesRepositoryImpl(
    private val httpClient: HttpClient
) : GamesRepository {

    private val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = false)

    override suspend fun fetchTrendingGames(): Result<Flow<PagingData<Games>>> {
        val pagingSource = BasePagingSource { page ->
            val response = httpClient.get(urlString = GAME_URL) {
                parameter("page", page)
            }.body<GamesDto>()
            response.toGames()
        }
        return runCatching {
            Pager(
                config = pagingConfig, pagingSourceFactory = { pagingSource }).flow
        }
    }

    override suspend fun fetchActionGames(): Result<Flow<PagingData<Games>>> {
        TODO("Not yet implemented")
    }


    override suspend fun fetchPopularGames(): Result<Flow<PagingData<Games>>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUpcomingGames(): Result<Flow<PagingData<Games>>> {
        TODO("Not yet implemented")
    }
}