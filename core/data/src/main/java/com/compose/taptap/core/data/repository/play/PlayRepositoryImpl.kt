package com.compose.taptap.core.data.repository.play

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.compose.taptap.core.data.datasource.local.LocalStorage
import com.compose.taptap.core.data.paging.CursorPage
import com.compose.taptap.core.data.paging.CursorPagingSource
import com.compose.taptap.core.data.repository.base.BaseRepository
import com.compose.taptap.core.domain.repository.PlayRepository
import com.compose.taptap.core.model.InstantGameItem
import com.compose.taptap.core.model.InstantGameRandomData
import com.compose.taptap.core.network.service.TapTapService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

class PlayRepositoryImpl(
    private val tapTapService: TapTapService,
    private val localStorage: LocalStorage,
    private val json: Json,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository(), PlayRepository {

    override fun fetchInstantGameStream(): Flow<PagingData<InstantGameItem>> {
        return createPager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE, prefetchDistance = 2, enablePlaceholders = false
            ), pagingSourceFactory = {
                CursorPagingSource { cursor ->
                    val response = tapTapService.getPlayGames(cursor)
                    val data = response.data
                    CursorPage(
                        items = data.list ?: emptyList(),
                        prevCursor = data.prevPage?.takeIf { it.isNotBlank() },
                        nextCursor = data.nextPage?.takeIf { it.isNotBlank() },
                    )
                }
            }).flowOn(
            dispatcher
        )
    }

    override fun getHistory(): List<InstantGameItem> {
        val jsonStr = localStorage.getString(KEY_HISTORY) ?: return emptyList()
        return runCatching { json.decodeFromString<List<InstantGameItem>>(jsonStr) }.getOrElse { emptyList() }
    }

    override fun saveHistory(list: List<InstantGameItem>) {
        val jsonStr = json.encodeToString(list)
        localStorage.putString(KEY_HISTORY, jsonStr)
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
            localStorage.putString(KEY_PLAYED, json.encodeToString(playedList))
        }
    }

    override fun getPlayed(): List<String> {
        val jsonStr = localStorage.getString(KEY_PLAYED) ?: return emptyList()
        return runCatching {
            json.decodeFromString<List<String>>(jsonStr)
        }.getOrElse { emptyList() }
    }

    override suspend fun getRandomInstantGame(): InstantGameRandomData {
        val response = tapTapService.getRandomInstantGame()
        return response.data
            ?: throw IllegalStateException("Random instant game data is unavailable")
    }

    companion object {
        private const val KEY_HISTORY = "history"
        private const val KEY_PLAYED = "played"
        private const val DEFAULT_PAGE_SIZE = 20
        private const val MAX_HISTORY_SIZE = 20
    }
}
