package com.compose.taptap.data.di

import android.content.Context
import android.content.SharedPreferences
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
import com.compose.taptap.ui.launcher.game.GameViewModel
import com.compose.taptap.ui.launcher.play.PlayViewModel
import com.compose.taptap.ui.launcher.search.SearchViewModel
import com.compose.taptap.ui.launcher.welcome.WelcomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun commonModule() = module {
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
