package com.example.kotlin_compose.data.datasources

import InstantGameItem
import InstantGameResponse
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kotlin_compose.data.network.utils.safeRequest
import com.example.kotlin_compose.data.paging.CursorPagingSource
import com.example.kotlin_compose.domain.repositories.PlayRepository
import com.example.kotlin_compose.domain.utils.Constants.INSTANT_GAME
import com.example.kotlin_compose.domain.utils.Constants.RECENTLY_INSTANT_GAME
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow

class PlayRepositoryImpl(private val httpClient: HttpClient) : PlayRepository {
    override fun fetchInstantGames(): Flow<PagingData<InstantGameItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20), pagingSourceFactory = {
                CursorPagingSource { cursor ->
                    val response = httpClient.get(urlString = INSTANT_GAME) {
                        parameter("from", 0)
                    }.body<InstantGameResponse>()
                    val data = response.data
                    val nextPageUrl = data.nextPage.takeIf { it.isNotBlank() }
                    data.list to nextPageUrl
                }
            }).flow
    }


    override suspend fun fetchRecentlyInstantsGames() = httpClient.safeRequest {
        get(RECENTLY_INSTANT_GAME).body<Any>()
    }
}