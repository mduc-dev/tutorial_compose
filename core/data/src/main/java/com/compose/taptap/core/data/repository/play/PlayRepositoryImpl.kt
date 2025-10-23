package com.compose.taptap.core.data.repository.play

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.compose.taptap.core.data.paging.CursorPage
import com.compose.taptap.core.data.paging.CursorPagingSource
import com.compose.taptap.core.data.repository.game.GamesRepositoryImpl.Companion.DEFAULT_PAGE_SIZE
import com.compose.taptap.core.model.InstantGameItem
import com.compose.taptap.core.network.service.TapTapService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

class PlayRepositoryImpl(
    private val tapTapService: TapTapService,
    private val prefs: SharedPreferences,
    private val json: Json,
    private val dispatcher: CoroutineDispatcher
) : PlayRepository {
    companion object {
        private const val KEY_HISTORY = "history"
        private const val KEY_PLAYED = "played"
        private const val MAX_HISTORY_SIZE = 20
    }

    override fun fetchInstantGameStream(): Flow<PagingData<InstantGameItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE, prefetchDistance = 2, enablePlaceholders = false
            ), pagingSourceFactory = {
                CursorPagingSource { cursor ->
                    val response = tapTapService.getPlayGames(cursor)
                    val data = response.data
                    CursorPage(
                        items = data.list,
                        prevCursor = data.prevPage.takeIf { it.isNotBlank() },
                        nextCursor = data.nextPage.takeIf { it.isNotBlank() },
                    )
                }
            }).flow.flowOn(
            dispatcher
        )
    }

    override fun getHistory(): List<InstantGameItem> {
        val jsonStr = prefs.getString(KEY_HISTORY, null) ?: return emptyList()
        return runCatching { json.decodeFromString<List<InstantGameItem>>(jsonStr) }.getOrElse { emptyList() }
    }

    override fun saveHistory(list: List<InstantGameItem>) {
        val jsonStr = json.encodeToString(list)
        prefs.edit { putString(KEY_HISTORY, jsonStr) }
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

}
