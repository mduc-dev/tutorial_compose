package com.compose.taptap.data.mappers

import com.compose.taptap.data.loader.LoadingResult
import com.compose.taptap.data.loader.map
import com.compose.taptap.domain.models.Games

interface GamesDataMapper {
    fun map(data: LoadingResult<List<Games>>): LoadingResult<List<Games>>
}

class DefaultGameDataMapper : GamesDataMapper {
    override fun map(data: LoadingResult<List<Games>>) = data.map { games ->
        games.map { game ->
            Games(
                type = game.type,
                identification = game.identification,
                app = game.app,
                recReason = game.recReason,
                category = game.category,
                dailies = game.dailies
            )
        }
    }
}
