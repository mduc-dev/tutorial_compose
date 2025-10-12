package com.example.kotlin_compose.di

import WelcomeRemoteDataSourceImpl
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.kotlin_compose.data.datasources.PlayRepositoryImpl
import com.example.kotlin_compose.data.loader.DataLoader
import com.example.kotlin_compose.data.loader.DefaultGamesDataLoader
import com.example.kotlin_compose.data.loader.GamesDataLoader
import com.example.kotlin_compose.data.loader.RefreshTrigger
import com.example.kotlin_compose.data.mappers.DefaultGameDataMapper
import com.example.kotlin_compose.data.mappers.GamesDataMapper
import com.example.kotlin_compose.data.source.remote.GamesRemoteDataSource
import com.example.kotlin_compose.data.source.remote.GameRemoteDataSourceImpl
import com.example.kotlin_compose.data.source.remote.SearchRemoteDataSourceImpl
import com.example.kotlin_compose.domain.models.Games
import com.example.kotlin_compose.domain.repositories.DefaultGamesRepository
import com.example.kotlin_compose.domain.repositories.GamesRepository
import com.example.kotlin_compose.domain.repositories.PlayRepository
import com.example.kotlin_compose.domain.repositories.SearchRepository
import com.example.kotlin_compose.domain.repositories.WelcomeRepository
import com.example.kotlin_compose.domain.utils.Constants.BASE_URL
import com.example.kotlin_compose.presentation.navigation.AppComposeNavigator
import com.example.kotlin_compose.presentation.navigation.TapComposeNavigator
import com.example.kotlin_compose.presentation.screens.game.GameViewModel
import com.example.kotlin_compose.presentation.screens.play.PlayViewModel
import com.example.kotlin_compose.presentation.screens.search.SearchViewModel
import com.example.kotlin_compose.presentation.screens.welcome.WelcomeViewModel
import com.klitsie.dataloading.data.source.local.GamesLocalDataSource
import com.klitsie.dataloading.data.source.local.InMemoryGamesLocalDataSource
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
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
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
                json(get()) // dùng Json từ Koin
            }
        }
    }

    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    //data
    single<GamesLocalDataSource> { InMemoryGamesLocalDataSource() }
    single<GamesRemoteDataSource> { GameRemoteDataSourceImpl(get()) }
    single<GamesRepository> { DefaultGamesRepository(get(), get()) }

    single<SearchRepository> { SearchRemoteDataSourceImpl(get()) }
    single<WelcomeRepository> { WelcomeRemoteDataSourceImpl(get(), prefs = get()) }

    //presentation
    factory { RefreshTrigger() }
    factory<DataLoader<List<Games>>> { DataLoader() }
    factory<GamesDataLoader> { DefaultGamesDataLoader(get(), get()) }
    single<GamesDataMapper> { DefaultGameDataMapper() }


    single<AppComposeNavigator> { TapComposeNavigator() }
    single<PlayRepository> { PlayRepositoryImpl(get(), prefs = get(), json = get()) }

    viewModel {
        WelcomeViewModel(
            welcomeRepository = get(), prefs = get()
        )
    }

    viewModelOf(::SearchViewModel)

    viewModelOf(::GameViewModel)

    viewModelOf(::PlayViewModel)
}
