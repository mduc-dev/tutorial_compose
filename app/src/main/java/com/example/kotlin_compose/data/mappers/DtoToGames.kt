package com.example.kotlin_compose.data.mappers

import com.example.kotlin_compose.data.loader.LoadingResult
import com.example.kotlin_compose.data.loader.map
import com.example.kotlin_compose.domain.models.Games

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
