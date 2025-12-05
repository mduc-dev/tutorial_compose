package com.compose.taptap.core.data.repository.welcome

import android.content.SharedPreferences
import androidx.core.content.edit
import com.compose.taptap.core.domain.repository.WelcomeRepository
import com.compose.taptap.core.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class WelcomeRepositoryImpl(private val prefs: SharedPreferences) : WelcomeRepository {
    companion object {
        private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
    }

    override fun getAuthState(): Flow<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithGoogle(): Result<Unit> {
        return try {
            delay(500)
            prefs.edit {
                putBoolean(
                    KEY_IS_LOGGED_IN,
                    true
                )
            }
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun signInWithFacebook(): Result<Unit> {
        return try {
            delay(500)
            prefs.edit { putBoolean(KEY_IS_LOGGED_IN, true) }
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun signUp(
        type: String?, email: String, password: String
    ): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(
        email: String, password: String
    ): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            prefs.edit { remove(KEY_IS_LOGGED_IN) }
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override fun isUserLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    override fun setUserLoggedIn(isLoggedIn: Boolean) {
        prefs.edit { putBoolean(KEY_IS_LOGGED_IN, isLoggedIn) }
    }

}
