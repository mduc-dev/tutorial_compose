package com.example.kotlin_compose.data.network.utils

import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(val exception: Throwable) : ApiResult<Nothing>
    data object Loading : ApiResult<Nothing>
}

fun <T> HttpClient.safeRequest(
    block: suspend HttpClient.() -> T
): Flow<ApiResult<T>> = flow<ApiResult<T>> {
    val response = this@safeRequest.block()
    emit(ApiResult.Success(response))
}.onStart {
    emit(ApiResult.Loading)
}.catch { e ->
    emit(ApiResult.Error(e))
}