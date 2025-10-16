package com.compose.taptap.data.repository

import com.compose.taptap.data.source.local.GamesLocalDataSource
import com.compose.taptap.data.source.remote.GamesRemoteDataSource
import com.compose.taptap.data.model.Games
import com.compose.taptap.data.model.ListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

interface GamesRepository {
    fun getCachedGames(): Flow<Result<List<ListItem>>>

    fun refreshGames(): Flow<Result<Games>>

    fun getGameFlow(): Flow<Result<List<ListItem>>>
}

class GamesRepositoryImpl(
    private val localDataSource: GamesLocalDataSource,
    private val remoteDataSource: GamesRemoteDataSource,
) : GamesRepository {

    override fun getCachedGames(): Flow<Result<List<ListItem>>> = flow {
        emit(runCatching {
            localDataSource.getCachedGames().getOrThrow()
                .map { it.toDomain() }
        })
    }

    override fun refreshGames(): Flow<Result<Games>> = flow {
        emit(runCatching {
            val dto = remoteDataSource.fetchGames().getOrThrow()
            localDataSource.saveGames(dto.data.list)
            dto
        })
    }

    override fun getGameFlow(): Flow<Result<List<ListItem>>> =
        localDataSource.observeGames().map { list ->
            Result.success(list.map { it.toDomain() })
        }
}


private fun ListItem.toDomain(): ListItem = ListItem(
    type = type,
    identification = identification,
    app = app,
    recReason = recReason,
    category = category,
    dailies = dailies
)
