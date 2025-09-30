package com.example.kotlin_compose.data.datasources

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kotlin_compose.data.network.models.GamesDto
import com.example.kotlin_compose.data.paging.BasePagingSource
import com.example.kotlin_compose.domain.models.Games
import com.example.kotlin_compose.domain.repositories.GamesRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

//class GamesRepositoryImpl(
//    private val httpClient: HttpClient
//) : GamesRepository {
//
//    private val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = false)
//
//    override fun fetchTrendingGames(): Flow<PagingData<Games>> {
//        return Pager(
//            config = pagingConfig, pagingSourceFactory = {
//                BasePagingSource { page ->
//                    val response = httpClient.get(urlString = GAME_URL) {
//                        parameter("page", page)
//                    }.body<GamesDto>()
//                    response.toGames()
//                }
//            }).flow
//    }
//
//    override fun fetchActionGames(): Flow<PagingData<Games>> {
//        return emptyFlow()
//    }
//
//
//    override fun fetchPopularGames(): Flow<PagingData<Games>> {
//        return emptyFlow()
//    }
//
//    override fun fetchUpcomingGames(): Flow<PagingData<Games>> {
//        return emptyFlow()
//    }
//}
