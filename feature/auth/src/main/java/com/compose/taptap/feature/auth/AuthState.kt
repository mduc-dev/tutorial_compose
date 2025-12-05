package com.compose.taptap.feature.auth

sealed class AuthState {
    object Idle : AuthState()
    data class Loading(val provider: Provider? = null) : AuthState()
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}

enum class Provider { Facebook, Google }
