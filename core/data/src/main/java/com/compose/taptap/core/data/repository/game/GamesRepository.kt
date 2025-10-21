package com.compose.taptap.core.data.repository.game

import androidx.paging.PagingData
import com.compose.taptap.core.model.ListGameItem
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun gamesStream(): Flow<PagingData<ListGameItem>>
}
