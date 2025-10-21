package com.compose.taptap.core.data.repository.search

import com.compose.taptap.core.network.model.SearchResponse
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl : SearchRepository {
    override fun fetchSearchPlaceholder(): Flow<SearchResponse> {
        TODO("Not yet implemented")
    }
}