package com.compose.taptap.domain.repositories

import GamesLocalDataSource
import com.compose.taptap.data.network.models.ListItem
import com.compose.taptap.data.source.remote.GamesRemoteDataSource
import com.compose.taptap.domain.models.Games
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getCachedGames(): Result<List<Games>>

    suspend fun refreshGames(): Result<List<Games>>

    fun observeGames(): Flow<List<Games>>
}

class DefaultGamesRepository(
    private val localDataSource: GamesLocalDataSource,
    private val remoteDataSource: GamesRemoteDataSource,
) : GamesRepository {

    override fun getCachedGames(): Result<List<Games>> = localDataSource.getCachedGames()

    override suspend fun refreshGames(): Result<List<Games>> =
        remoteDataSource.fetchGames().map { dto ->
            val games = dto.data.list.map(ListItem::toDomain)
            localDataSource.saveGames(games)
            games
        }


    override fun observeGames(): Flow<List<Games>> = localDataSource.observeGames()
}

private fun ListItem.toDomain(): Games = Games(
    type = type,
    identification = identification,
    app = app,
    recReason = recReason,
    category = category,
    dailies = dailies
)
