package com.compose.taptap.core.data.repository.game

import com.compose.taptap.core.data.model.Games
import com.compose.taptap.core.model.ListGameItem
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getCachedGames(): Flow<Result<List<ListGameItem>>>

    fun refreshGames(): Flow<Result<Games>>

    fun getGames(): Flow<Result<List<ListGameItem>>>
}


private fun ListGameItem.toDomain(): ListGameItem = ListGameItem(
    type = type,
    identification = identification,
    app = app,
    recReason = recReason,
    category = category,
    dailies = dailies
)
