package com.compose.taptap.di


import com.compose.taptap.core.domain.usecases.game.GetGameFlowUseCase
import com.compose.taptap.core.domain.usecases.me.GetUserProfileUseCase
import com.compose.taptap.core.domain.usecases.play.AddGameToHistoryUseCase
import com.compose.taptap.core.domain.usecases.play.GetGameHistoryUseCase
import com.compose.taptap.core.domain.usecases.play.GetPlayGameFlowUseCase
import com.compose.taptap.core.domain.usecases.play.GetRandomInstantGameUseCase
import com.compose.taptap.core.domain.usecases.play.MarkGameAsPlayedUseCase
import com.compose.taptap.core.domain.usecases.search.GetSearchPlaceholderFlowUseCase
import com.compose.taptap.core.viewmodel.loader.newRefreshTrigger
import com.compose.taptap.feature.auth.welcome.WelcomeViewModel
import com.compose.taptap.feature.game.GameViewModel
import com.compose.taptap.feature.me.MeViewModel
import com.compose.taptap.feature.me.badge.BadgeViewModel
import com.compose.taptap.feature.notifications.NotificationViewModel
import com.compose.taptap.feature.play.PlayViewModel
import com.compose.taptap.feature.search.SearchViewModel
import com.compose.taptap.feature.tavern.TavernViewModel
import com.tencent.mmkv.MMKV
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun commonModule() = module {
    single {
        MMKV.defaultMMKV()
    }
    //presentation
    factory { newRefreshTrigger() }

    //use-case
    factory<GetGameFlowUseCase> { GetGameFlowUseCase(get()) }
    factory<GetSearchPlaceholderFlowUseCase> { GetSearchPlaceholderFlowUseCase(get()) }
    factory<GetRandomInstantGameUseCase> { GetRandomInstantGameUseCase(get()) }
    factory<GetPlayGameFlowUseCase> { GetPlayGameFlowUseCase(get()) }
    factory<AddGameToHistoryUseCase> { AddGameToHistoryUseCase(get()) }
    factory<MarkGameAsPlayedUseCase> { MarkGameAsPlayedUseCase(get()) }
    factory<GetGameHistoryUseCase> { GetGameHistoryUseCase(get()) }
    factory { GetUserProfileUseCase(get()) }

    viewModel {
        WelcomeViewModel(
            welcomeRepository = get()
        )
    }

    viewModelOf(::SearchViewModel)

    viewModelOf(::GameViewModel)

    viewModelOf(::PlayViewModel)

    viewModelOf(::MeViewModel)
    viewModelOf(::BadgeViewModel)
    viewModelOf(::NotificationViewModel)
    viewModelOf(::TavernViewModel)
}
