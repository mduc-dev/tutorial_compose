package com.compose.taptap.data.source.remote

import InstantGameItem
import InstantGameResponse
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.compose.taptap.data.paging.CursorPagingSource
import com.compose.taptap.data.repository.PlayRepository
import com.compose.taptap.domain.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import java.net.URLDecoder

class PlayRemoteDataSourceImpl(
    private val httpClient: HttpClient, private val prefs: SharedPreferences, private val json: Json
) : PlayRepository {

    companion object {
        private const val KEY_HISTORY = "history"
        private const val KEY_PLAYED = "played"
        private const val MAX_HISTORY_SIZE = 20
    }

    override fun fetchInstantGames(): Flow<PagingData<InstantGameItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 1,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                CursorPagingSource { cursor ->
                    val response = httpClient.get(urlString = Constants.INSTANT_GAME) {
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


    override fun getHistory(): List<InstantGameItem> {
        val jsonStr = prefs.getString(KEY_HISTORY, null) ?: return emptyList()
        return runCatching { json.decodeFromString<List<InstantGameItem>>(jsonStr) }.getOrElse { emptyList() }
    }


    override fun saveHistory(list: List<InstantGameItem>) {
        val jsonStr = json.encodeToString(list)
        prefs.edit { putString(KEY_HISTORY, jsonStr) }
    }

    override fun markPlayed(gameId: String) {
        val playedList = getPlayed().toMutableList()
        if (!playedList.contains(gameId)) {
            playedList.add(gameId)
            prefs.edit { putString(KEY_PLAYED, json.encodeToString(playedList)) }
        }
    }

    override fun getPlayed(): List<String> {
        val jsonStr = prefs.getString(KEY_PLAYED, null) ?: return emptyList()
        return runCatching {
            json.decodeFromString<List<String>>(jsonStr)
        }.getOrElse { emptyList() }
    }

    override fun addToHistory(game: InstantGameItem) {
        val history = getHistory().toMutableList()
        history.removeAll { it.identification == game.identification }
        history.add(0, game)
        if (history.size > MAX_HISTORY_SIZE) {
            history.removeLastOrNull()
        }
        saveHistory(history)
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