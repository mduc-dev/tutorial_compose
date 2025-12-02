package com.compose.taptap.core.domain.repository

import com.compose.taptap.core.model.Search
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    /** Fetch Search placeholder from data source */
    fun fetchSearchPlaceholder(): Flow<Search>
}
