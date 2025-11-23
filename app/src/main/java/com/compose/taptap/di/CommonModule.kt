package com.compose.taptap.di

import android.content.Context
import android.content.SharedPreferences
import com.compose.taptap.core.data.loader.RefreshTrigger
import com.compose.taptap.core.domain.usecases.game.GetGameFlowUseCase
import com.compose.taptap.core.domain.usecases.search.GetSearchPlaceholderFlowUseCase
import com.compose.taptap.core.navigation.AppComposeNavigator
import com.compose.taptap.core.navigation.TapComposeNavigator
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.feature.game.GameViewModel
import com.compose.taptap.ui.launcher.play.PlayViewModel
import com.compose.taptap.feature.search.SearchViewModel
import com.compose.taptap.ui.launcher.welcome.WelcomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun commonModule() = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
    //presentation
    factory { RefreshTrigger() }

    //use-case
    factory<GetGameFlowUseCase> { GetGameFlowUseCase(get()) }
    factory<GetSearchPlaceholderFlowUseCase> { GetSearchPlaceholderFlowUseCase(get()) }

    single<AppComposeNavigator<TapTapScreen>> { TapComposeNavigator() }

    viewModel {
        WelcomeViewModel(
            welcomeRepository = get(), prefs = get()
        )
    }

    viewModelOf(::SearchViewModel)

    viewModelOf(::GameViewModel)

    viewModelOf(::PlayViewModel)
}
