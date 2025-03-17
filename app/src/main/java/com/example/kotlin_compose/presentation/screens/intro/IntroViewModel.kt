package com.example.kotlin_compose.presentation.screens.intro

import androidx.lifecycle.ViewModel
import com.example.kotlin_compose.domain.repositories.GamesRepository
import com.example.kotlin_compose.domain.repositories.IntroRepository
import com.example.kotlin_compose.presentation.utils.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class IntroViewModel(private val introRepository: IntroRepository) : ViewModel() {
    private val _introUiState = MutableStateFlow<AuthState>(AuthState.Loading)
    val introUiState = _introUiState.asStateFlow()

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

}