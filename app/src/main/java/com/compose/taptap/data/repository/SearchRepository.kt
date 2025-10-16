package com.compose.taptap.data.repository

import com.compose.taptap.network.models.SearchPlaceHolder
import com.compose.taptap.network.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    /** Fetch Search placeholder from data source*/
    fun fetchSearchPlaceholder(): Flow<ApiResult<SearchPlaceHolder>>
}
