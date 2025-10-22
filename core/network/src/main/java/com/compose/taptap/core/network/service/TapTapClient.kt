package com.compose.taptap.core.network.service

import com.compose.taptap.core.network.di.BUILDCONFIG
import com.compose.taptap.core.network.model.GameResponse
import com.compose.taptap.core.network.model.PlayGameResponse
import com.compose.taptap.core.network.model.SearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TapTapClient(private val httpClient: HttpClient) : TapTapService {
    override suspend fun getGames(cursor: String?): GameResponse {
        val url = cursor?.takeIf { it.isNotBlank() }?.let(::resolveCursorUrl) ?: BUILDCONFIG.gameUrl()
        return httpClient.get(url).body()
    }

    override suspend fun getPlayGames(cursor: String?): PlayGameResponse {
        return httpClient.get(BUILDCONFIG.instantPlay()).body()
    }

    override suspend fun getSearchPlaceholder(): SearchResponse {
        return httpClient.get(BUILDCONFIG.instantPlay()).body()
    }

    private fun resolveCursorUrl(cursor: String): String {
        val trimmed = cursor.trim()
        return when {
            trimmed.startsWith("http", ignoreCase = true) -> trimmed
            trimmed.startsWith("/") -> "${BUILDCONFIG.BASE_URL}$trimmed"
            else -> "${BUILDCONFIG.BASE_URL}/$trimmed"
        }
    }
}
