package com.example.kotlin_compose.presentation.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_compose.domain.repositories.WelcomeRepository
import com.example.kotlin_compose.presentation.utils.AuthState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WelcomeViewModel(private val welcomeRepository: WelcomeRepository) : ViewModel() {
    private val _welcomeUiState = MutableStateFlow<AuthState>(AuthState.Loading)
    val welcomeUiState = _welcomeUiState.asStateFlow()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
    }

    init {
        // Check initial auth state, e.g. from SharedPreferences or Firebase
        checkInitialAuthState()
    }

    private fun checkInitialAuthState() {
        // Check if user is logged in from SharedPreferences or other storage
        val isLoggedIn = false // Replace with actual logic

        if (isLoggedIn) {
            _welcomeUiState.value = AuthState.Authenticated
        } else {
            _welcomeUiState.value = AuthState.Unauthenticated
        }
    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        welcomeRepository.signIn(email, password).onSuccess { data ->
            _welcomeUiState.value = AuthState.Authenticated
        }.onFailure { error ->
            _welcomeUiState.value = AuthState.Error(error.message ?: "Unknown error")
        }
    }

    fun signUp(email: String, password: String) = viewModelScope.launch {
        welcomeRepository.signUp("", email, password).onSuccess { data ->
            _welcomeUiState.value = AuthState.Authenticated
        }.onFailure { error ->
            _welcomeUiState.value = AuthState.Error(error.message ?: "Unknown error")
        }
    }

    fun signInWithFaceBook() = viewModelScope.launch(coroutineExceptionHandler) {
        welcomeRepository.signInWithFacebook().onSuccess { data ->
            _welcomeUiState.value = AuthState.Authenticated
        }.onFailure { error ->
            _welcomeUiState.value = AuthState.Error(error.message ?: "Unknown error")
        }
    }

    fun signInWithGoogle() = viewModelScope.launch(coroutineExceptionHandler) {
        welcomeRepository.signInWithGoogle().onSuccess { data ->
            _welcomeUiState.value = AuthState.Authenticated
        }.onFailure { error ->
            _welcomeUiState.value = AuthState.Error(error.message ?: "Unknown error")
        }
    }

}