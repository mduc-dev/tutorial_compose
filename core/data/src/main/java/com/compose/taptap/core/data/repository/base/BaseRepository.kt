package com.compose.taptap.core.data.repository.base

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.compose.taptap.core.model.DataError
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import java.net.ConnectException
import java.net.SocketTimeoutException
import kotlinx.coroutines.CancellationException
import java.io.IOException

/**
 * Created by duc on 09/12/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */
abstract class BaseRepository {
    /**
     * Utility to create a Pager flow.
     * Use this when you need pagination.
     */
    protected fun <T : Any> createPager(
        config: PagingConfig,
        pagingSourceFactory: () -> PagingSource<String, T>
    ): Flow<PagingData<T>> {
        return Pager(
            config = config,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


    /**
     * Execute a suspend block and return its result as a Result<T>.
     * Captures and encapsulates exceptions.
     */
    protected suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.success(apiCall())
        } catch (e: Exception) {
            val dataError = when (e) {
                is HttpRequestTimeoutException -> DataError.SendTimeout
                is SocketTimeoutException -> DataError.ReceiveTimeout
                is ConnectException -> DataError.ConnectionError
                is java.nio.channels.ClosedChannelException, is IOException -> DataError.ConnectionError
                is ClientRequestException -> DataError.BadResponse
                is ServerResponseException -> DataError.BadResponse
                is CancellationException -> throw e
                else -> DataError.Unknown(e)
            }
            Result.failure(dataError)
        }
    }

    /**
     * Create a flow that executes the block on the specified dispatcher.
     * Defaults to Dispatchers.IO.
     */
    protected fun <T> safeFlow(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> T
    ): Flow<T> = flow {
        emit(block())
    }.flowOn(dispatcher)
}
