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
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import java.net.URLDecoder

class PlayRepositoryImpl(private val httpClient: HttpClient) : PlayRepository {
    override fun fetchInstantGames(): Flow<PagingData<InstantGameItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 1,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                CursorPagingSource { cursor ->
                    val response = httpClient.get(urlString = INSTANT_GAME) {
                        if (cursor.isNullOrBlank()) {
                            parameter("from", 0)
                        } else {
                            applyCursorParameters(cursor)
                        }
                    }.body<InstantGameResponse>()
                    val data = response.data
                    val nextCursor = data.nextPage.takeIf { it.isNotBlank() }
                    data.list to nextCursor
                }
            }).flow
    }


    override suspend fun fetchRecentlyInstantsGames() = httpClient.safeRequest {
        get(RECENTLY_INSTANT_GAME).body<Any>()
    }
}

private fun HttpRequestBuilder.applyCursorParameters(cursor: String) {
    val query = cursor.substringAfter('?', missingDelimiterValue = "").trim()
    if (query.isBlank()) return

    query.split('&').asSequence().mapNotNull { parameter ->
        if (parameter.isBlank()) return@mapNotNull null
        val parts = parameter.split('=', limit = 2)
        if (parts.size != 2) return@mapNotNull null
        val (rawKey, rawValue) = parts
        val key = URLDecoder.decode(rawKey, Charsets.UTF_8.name())
        if (key.equals("X-UA", ignoreCase = true)) return@mapNotNull null
        val value = URLDecoder.decode(rawValue, Charsets.UTF_8.name())
        key to value
    }.forEach { (key, value) ->
        parameter(key, value)
    }
}