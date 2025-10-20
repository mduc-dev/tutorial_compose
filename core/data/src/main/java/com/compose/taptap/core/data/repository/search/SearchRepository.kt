package com.compose.taptap.core.data.repository.search

import com.compose.taptap.core.network.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    /** Fetch Search placeholder from data source*/
    fun fetchSearchPlaceholder(): Flow<SearchResponse>
}