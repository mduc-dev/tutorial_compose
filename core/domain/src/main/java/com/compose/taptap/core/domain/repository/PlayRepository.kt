package com.compose.taptap.core.domain.repository

import androidx.paging.PagingData
import com.compose.taptap.core.model.InstantGameItem
import com.compose.taptap.core.model.InstantGameRandomResponse
import kotlinx.coroutines.flow.Flow

interface PlayRepository {
    fun fetchInstantGameStream(): Flow<PagingData<InstantGameItem>>

    fun getHistory(): List<InstantGameItem>

    fun saveHistory(list: List<InstantGameItem>)

    fun addToHistory(game: InstantGameItem)

    fun markPlayed(gameId: String)

    fun getPlayed(): List<String>

    suspend fun getRandomInstantGame(): InstantGameRandomResponse
}
