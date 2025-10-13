package com.compose.taptap.di

import GamesLocalDataSource
import InMemoryGamesLocalDataSource
import WelcomeRemoteDataSourceImpl
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.navigation.compose.ComposeNavigator
import com.compose.taptap.core.navigation.AppComposeNavigator
import com.compose.taptap.core.navigation.TapComposeNavigator
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.data.loader.DataLoader
import com.compose.taptap.data.loader.DefaultGamesDataLoader
import com.compose.taptap.data.loader.GamesDataLoader
import com.compose.taptap.data.loader.RefreshTrigger
import com.compose.taptap.data.mappers.DefaultGameDataMapper
import com.compose.taptap.data.mappers.GamesDataMapper
import com.compose.taptap.data.source.remote.GameRemoteDataSourceImpl
import com.compose.taptap.data.source.remote.GamesRemoteDataSource
import com.compose.taptap.data.source.remote.PlayRemoteDataSourceImpl
import com.compose.taptap.data.source.remote.SearchRemoteDataSourceImpl
import com.compose.taptap.domain.models.Games
import com.compose.taptap.domain.repositories.DefaultGamesRepository
import com.compose.taptap.domain.repositories.GamesRepository
import com.compose.taptap.domain.repositories.PlayRepository
import com.compose.taptap.domain.repositories.SearchRepository
import com.compose.taptap.domain.repositories.WelcomeRepository
import com.compose.taptap.domain.utils.Constants
import com.compose.taptap.ui.launcher.game.GameViewModel
import com.compose.taptap.ui.launcher.play.PlayViewModel
import com.compose.taptap.ui.launcher.search.SearchViewModel
import com.compose.taptap.ui.launcher.welcome.WelcomeViewModel
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
                    host = Constants.BASE_URL
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


    single<AppComposeNavigator<TapTapScreen>> { TapComposeNavigator() }

    single<PlayRepository> { PlayRemoteDataSourceImpl(get(), prefs = get(), json = get()) }

    viewModel {
        WelcomeViewModel(
            welcomeRepository = get(), prefs = get()
        )
    }

    viewModelOf(::SearchViewModel)

    viewModelOf(::GameViewModel)

    viewModelOf(::PlayViewModel)
}
