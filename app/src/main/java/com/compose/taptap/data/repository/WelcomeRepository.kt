package com.compose.taptap.data.repository

import com.compose.taptap.data.model.User
import com.compose.taptap.ui.utils.AuthState
import kotlinx.coroutines.flow.Flow

interface WelcomeRepository {
    fun getAuthState(): Flow<AuthState>

    suspend fun signInWithGoogle(): Result<Unit> //replace temporary Unit with User

    suspend fun signInWithFacebook(): Result<Unit> //replace temporary Unit with User

    suspend fun signUp(type: String?, email: String, password: String): Result<User>

    suspend fun signIn(email: String, password: String): Result<User>

    suspend fun signOut(): Result<Unit>
}
