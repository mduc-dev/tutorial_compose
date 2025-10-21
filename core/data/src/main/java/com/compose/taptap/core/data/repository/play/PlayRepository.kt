package com.compose.taptap.core.data.repository.play

import androidx.paging.PagingData
import com.compose.taptap.core.model.InstantGameItem
import kotlinx.coroutines.flow.Flow

interface PlayRepository {
    fun fetchInstantGameStream(): Flow<PagingData<InstantGameItem>>

    fun getHistory(): List<InstantGameItem>

    fun saveHistory(list: List<InstantGameItem>)

    fun addToHistory(game: InstantGameItem)

    fun markPlayed(gameId: String)

    fun getPlayed(): List<String>
}