package com.compose.taptap.domain.repositories

import com.compose.taptap.data.network.models.SearchPlaceHolder
import com.compose.taptap.data.network.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    /** Fetch Search placeholder from data source*/
    fun fetchSearchPlaceholder(): Flow<ApiResult<SearchPlaceHolder>>
}
