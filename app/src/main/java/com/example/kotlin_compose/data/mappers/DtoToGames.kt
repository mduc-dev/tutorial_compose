package com.example.kotlin_compose.data.mappers

import android.util.Log
import com.example.kotlin_compose.data.network.models.GamesDto
import com.example.kotlin_compose.domain.models.Games

fun GamesDto.toGames(): List<Games> {
    return data.list.map { listItem ->
        Log.d("RESPONSE", "$listItem")
        Games(
            type = listItem.type,
            identification = listItem.identification,
            app = listItem.app,
            recReason = listItem.recReason
        )
    }
}

