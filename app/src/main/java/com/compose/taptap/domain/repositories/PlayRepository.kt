package com.compose.taptap.domain.repositories

import InstantGameItem
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface PlayRepository {
    fun fetchInstantGames(): Flow<PagingData<InstantGameItem>>

    fun getHistory(): List<InstantGameItem>

    fun saveHistory(list: List<InstantGameItem>)

    fun addToHistory(game: InstantGameItem)

    fun markPlayed(gameId: String)

    fun getPlayed(): List<String>
}