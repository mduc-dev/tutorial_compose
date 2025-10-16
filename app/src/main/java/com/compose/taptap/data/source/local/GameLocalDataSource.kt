package com.compose.taptap.data.source.local

import com.compose.taptap.data.model.ListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

/**
 * In-memory cache for games data.
 */
interface GamesLocalDataSource {
    fun getCachedGames(): Result<List<ListItem>>

    fun saveGames(games: List<ListItem>)

    fun observeGames(): Flow<List<ListItem>>
}

class InMemoryGamesLocalDataSource : GamesLocalDataSource {
    private val gamesState = MutableStateFlow<List<ListItem>>(emptyList())

    override fun getCachedGames(): Result<List<ListItem>> {
        val current = gamesState.value
        return if (current.isEmpty()) {
            failure(IllegalStateException("No cached games available"))
        } else {
            success(current)
        }
    }

    override fun saveGames(games: List<ListItem>) {
        gamesState.value = games
    }

    override fun observeGames(): Flow<List<ListItem>> = gamesState.asStateFlow()
}
