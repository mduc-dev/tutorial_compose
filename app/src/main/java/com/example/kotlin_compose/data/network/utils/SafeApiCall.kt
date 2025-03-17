package com.example.kotlin_compose.data.network.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

suspend fun <T : Any?> safeApiCall(apiCall: suspend () -> T): Result<Flow<T>> {
    return runCatching {
        flowOf(apiCall.invoke())
    }
}