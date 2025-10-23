package com.compose.taptap.core.network.service

import com.compose.taptap.core.network.di.BUILDCONFIG
import com.compose.taptap.core.network.model.GameResponse
import com.compose.taptap.core.network.model.PlayGameResponse
import com.compose.taptap.core.network.model.SearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TapTapClient(private val httpClient: HttpClient) : TapTapService {
    private val gameXua: String by lazy { BUILDCONFIG.newXUA() }
    private val playXua: String by lazy { BUILDCONFIG.newXUA() }

    override suspend fun getGames(cursor: String?): GameResponse {
        val url = cursor?.takeIf { it.isNotBlank() }?.let { resolveCursorUrl(it, gameXua) }
            ?: BUILDCONFIG.gameUrl(xua = gameXua)
        return httpClient.get(url).body()
    }

    override suspend fun getPlayGames(cursor: String?): PlayGameResponse {
        val url = cursor?.takeIf { it.isNotBlank() }?.let { resolveCursorUrl(it, playXua) }
            ?: BUILDCONFIG.instantPlay(xua = playXua)
        return httpClient.get(url).body()
    }

    override suspend fun getSearchPlaceholder(): SearchResponse {
        return httpClient.get(BUILDCONFIG.searchPlaceholder()).body()
    }

    private fun resolveCursorUrl(cursor: String, xua: String): String {
        val trimmed = cursor.trim()
        val resolved = when {
            trimmed.startsWith("http", ignoreCase = true) -> trimmed
            trimmed.startsWith("/") -> "${BUILDCONFIG.BASE_URL}$trimmed"
            else -> "${BUILDCONFIG.BASE_URL}/$trimmed"
        }
        return BUILDCONFIG.ensureXua(resolved, xua)
    }
}
