package com.klitsie.dataloading.data.source.local

import com.example.kotlin_compose.domain.models.Games
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

/**
 * In-memory cache for games data.
 */
interface GamesLocalDataSource {

    fun getCachedGames(): Result<List<Games>>

    fun saveGames(games: List<Games>)

    fun observeGames(): Flow<List<Games>>
}

class InMemoryGamesLocalDataSource : GamesLocalDataSource {

    private val gamesState = MutableStateFlow<List<Games>>(emptyList())

    override fun getCachedGames(): Result<List<Games>> {
        val current = gamesState.value
        return if (current.isEmpty()) {
            failure(IllegalStateException("No cached games available"))
        } else {
            success(current)
        }
    }

    override fun saveGames(games: List<Games>) {
        gamesState.value = games
    }

    override fun observeGames(): Flow<List<Games>> = gamesState.asStateFlow()
}
