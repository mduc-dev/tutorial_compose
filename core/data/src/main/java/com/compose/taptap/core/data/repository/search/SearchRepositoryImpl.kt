package com.compose.taptap.core.data.repository.search

import com.compose.taptap.core.model.Search
import kotlinx.coroutines.flow.Flow

interface SearchRepositoryImpl {
    /** Fetch Search placeholder from data source*/
    fun fetchSearchPlaceholder(): Flow<Search>
}