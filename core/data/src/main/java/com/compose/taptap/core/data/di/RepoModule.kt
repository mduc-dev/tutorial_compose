package com.compose.taptap.core.data.di

import com.compose.taptap.core.data.repository.game.GamesRepository
import com.compose.taptap.core.data.repository.game.GamesRepositoryImpl
import com.compose.taptap.core.data.repository.play.PlayRepository
import com.compose.taptap.core.data.repository.play.PlayRepositoryImpl
import com.compose.taptap.core.data.repository.search.SearchRepository
import com.compose.taptap.core.data.repository.search.SearchRepositoryImpl
import com.compose.taptap.core.data.repository.welcome.WelcomeRepository
import com.compose.taptap.core.data.repository.welcome.WelcomeRepositoryImpl
import com.compose.taptap.core.network.di.TapTapAppDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun repoModule() = module {
    single<WelcomeRepository> { WelcomeRepositoryImpl(get()) }
    single<GamesRepository> {
        GamesRepositoryImpl(
            tapTapService = get(),
            dispatcher = get<CoroutineDispatcher>(named(TapTapAppDispatcher.IO))
        )
    }
    single<PlayRepository> {
        PlayRepositoryImpl(
            get(), get(), get(), get<CoroutineDispatcher>(named(TapTapAppDispatcher.IO))
        )
    }
    single<SearchRepository> { SearchRepositoryImpl() }
}
