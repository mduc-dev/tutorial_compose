package com.compose.taptap.core.data.repository.search

import com.compose.taptap.core.network.model.SearchResponse
import com.compose.taptap.core.network.service.TapTapService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchRepositoryImpl(private val tapTapService: TapTapService) : SearchRepository {
    override fun fetchSearchPlaceholder(): Flow<SearchResponse> {
        return flow {
            val response = tapTapService.getSearchPlaceholder()
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}