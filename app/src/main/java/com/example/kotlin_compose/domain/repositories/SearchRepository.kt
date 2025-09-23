package com.example.kotlin_compose.domain.repositories

import com.example.kotlin_compose.data.network.utils.ApiResult
import com.example.kotlin_compose.data.network.models.SearchPlaceHolder
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    /** Fetch Search placeholder from data source*/
    fun fetchSearchPlaceholder(): Flow<ApiResult<SearchPlaceHolder>>
}
