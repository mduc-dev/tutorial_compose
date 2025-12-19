package com.compose.taptap.core.domain.repository

import androidx.paging.PagingData
import com.compose.taptap.core.model.InstantGameItem
import com.compose.taptap.core.model.InstantGameRandomData
import kotlinx.coroutines.flow.Flow

interface PlayRepository {
    fun fetchInstantGameStream(): Flow<PagingData<InstantGameItem>>

    suspend fun getHistory(): List<InstantGameItem>

    suspend fun saveHistory(list: List<InstantGameItem>)

    suspend fun addToHistory(game: InstantGameItem)

    suspend fun markPlayed(gameId: String)

    suspend fun getPlayed(): List<String>

    suspend fun getRandomInstantGame(): InstantGameRandomData
}
