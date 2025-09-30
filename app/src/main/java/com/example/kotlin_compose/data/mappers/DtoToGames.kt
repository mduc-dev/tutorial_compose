package com.example.kotlin_compose.data.mappers

import com.example.kotlin_compose.data.loader.LoadingResult
import com.example.kotlin_compose.data.loader.map
//import com.example.kotlin_compose.data.network.models.GamesDto
import com.example.kotlin_compose.domain.models.Games

//fun GamesDto.toGames(): List<Games> {
//    return data.list.map {
//        Games(
//            type = it.type,
//            identification = it.identification,
//            app = it.app,
//            recReason = it.recReason
//        )
//    }
//}

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
                category = game.category
            )
        }
    }
}
