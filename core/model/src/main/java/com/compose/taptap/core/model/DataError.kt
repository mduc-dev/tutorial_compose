package com.compose.taptap.core.model

/**
 * Created by duc on 09/12/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */
sealed class DataError : Exception() {
    data object ConnectionTimeout : DataError()
    data object SendTimeout : DataError()
    data object ReceiveTimeout : DataError()
    data object ConnectionError : DataError()
    data object BadResponse : DataError()
    data object Cancelled : DataError()
    data class Unknown(val originalException: Throwable? = null) : DataError()
}
