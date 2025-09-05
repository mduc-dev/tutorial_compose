package com.example.kotlin_compose.presentation.screens.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_compose.domain.repositories.IntroRepository
import com.example.kotlin_compose.presentation.utils.AuthState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IntroViewModel(private val introRepository: IntroRepository) : ViewModel() {
    private val _introUiState = MutableStateFlow<AuthState>(AuthState.Loading)
    val introUiState = _introUiState.asStateFlow()
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
            _introUiState.value = AuthState.Authenticated
        } else {
            _introUiState.value = AuthState.Unauthenticated
        }
    }
     fun signIn(email: String, password: String) = viewModelScope.launch {
        introRepository.signIn(email, password).onSuccess { data ->
            _introUiState.value = AuthState.Authenticated
        }.onFailure { error ->
            _introUiState.value = AuthState.Error(error.message ?: "Unknown error")
        }
    }
     fun signUp(email: String, password: String) = viewModelScope.launch {
introRepository.signUp("",email,password).onSuccess { data->
    _introUiState.value = AuthState.Authenticated
}.onFailure { error-> _introUiState.value = AuthState.Error(error.message ?: "Unknown error") }
    }
     fun signInWithFaceBook() = viewModelScope.launch(coroutineExceptionHandler) {
        introRepository.signInWithFacebook().onSuccess { data ->
            _introUiState.value = AuthState.Authenticated
        }.onFailure { error ->
            _introUiState.value = AuthState.Error(error.message ?: "Unknown error")
        }
    }
     fun signInWithGoogle() = viewModelScope.launch(coroutineExceptionHandler) {
        introRepository.signInWithGoogle().onSuccess { data ->
            _introUiState.value = AuthState.Authenticated
        }.onFailure { error ->
            _introUiState.value = AuthState.Error(error.message ?: "Unknown error")
        }
    }

}