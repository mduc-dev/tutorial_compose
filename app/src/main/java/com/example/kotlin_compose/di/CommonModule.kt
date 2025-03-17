package com.example.kotlin_compose.di

import android.util.Log
import com.example.kotlin_compose.data.datasources.GamesRepositoryImpl
import com.example.kotlin_compose.domain.repositories.GamesRepository
import com.example.kotlin_compose.domain.utils.Constants.BASE_URL
import com.example.kotlin_compose.presentation.screens.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.addDefaultResponseValidation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

fun commonModule() = module {
    /**
     * Creates a http client for Ktor that is provided to the
     * API client via constructor injection
     */
    single {
        HttpClient {
            expectSuccess = true
            addDefaultResponseValidation()

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                }
            }

            install(Logging) {
                level = LogLevel.HEADERS
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Http Client", message)
                    }
                }
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    single<GamesRepository> { GamesRepositoryImpl(get()) }

    viewModelOf(::HomeViewModel)
}

