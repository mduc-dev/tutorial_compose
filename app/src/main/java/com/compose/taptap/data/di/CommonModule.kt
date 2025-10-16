package com.compose.taptap.data.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.compose.taptap.core.navigation.AppComposeNavigator
import com.compose.taptap.core.navigation.TapComposeNavigator
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.data.util.RefreshTrigger
import com.compose.taptap.data.source.local.GamesLocalDataSource
import com.compose.taptap.data.source.local.InMemoryGamesLocalDataSource
import com.compose.taptap.data.source.remote.GameRemoteDataSourceImpl
import com.compose.taptap.data.source.remote.GamesRemoteDataSource
import com.compose.taptap.data.source.remote.PlayRemoteDataSourceImpl
import com.compose.taptap.data.source.remote.SearchRemoteDataSourceImpl
import com.compose.taptap.data.source.remote.WelcomeRemoteDataSourceImpl
import com.compose.taptap.data.repository.GamesRepository
import com.compose.taptap.data.repository.GamesRepositoryImpl
import com.compose.taptap.data.repository.PlayRepository
import com.compose.taptap.data.repository.SearchRepository
import com.compose.taptap.data.repository.WelcomeRepository
import com.compose.taptap.domain.usecases.game.GetGameFlowUseCase
import com.compose.taptap.domain.usecases.search.GetSearchPlaceholderFlowUseCase
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
    single<GamesRepository> { GamesRepositoryImpl(get(), get()) }

    single<SearchRepository> { SearchRemoteDataSourceImpl(get()) }
    single<WelcomeRepository> { WelcomeRemoteDataSourceImpl(get(), prefs = get()) }


    //presentation
    factory { RefreshTrigger() }

    //use-case
    factory<GetGameFlowUseCase> { GetGameFlowUseCase(get()) }
    factory<GetSearchPlaceholderFlowUseCase> { GetSearchPlaceholderFlowUseCase(get()) }

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
