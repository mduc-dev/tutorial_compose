package com.example.kotlin_compose.domain.repositories

import InstantGameItem
import androidx.paging.PagingData
import com.example.kotlin_compose.data.network.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface PlayRepository {
    fun fetchInstantGames(): Flow<PagingData<InstantGameItem>>

    suspend fun fetchRecentlyInstantsGames(): Flow<ApiResult<Any>>
}