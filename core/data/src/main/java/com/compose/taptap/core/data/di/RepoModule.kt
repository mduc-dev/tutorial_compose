package com.compose.taptap.core.data.di

import com.compose.taptap.core.data.datasource.local.LocalStorage
import com.compose.taptap.core.data.datasource.local.MMKVStorageImpl
import com.compose.taptap.core.data.repository.MeRepositoryImpl
import com.compose.taptap.core.data.repository.game.GamesRepositoryImpl
import com.compose.taptap.core.data.repository.play.PlayRepositoryImpl
import com.compose.taptap.core.data.repository.search.SearchRepositoryImpl
import com.compose.taptap.core.data.repository.welcome.WelcomeRepositoryImpl
import com.compose.taptap.core.domain.repository.GamesRepository
import com.compose.taptap.core.domain.repository.MeRepository
import com.compose.taptap.core.domain.repository.PlayRepository
import com.compose.taptap.core.domain.repository.SearchRepository
import com.compose.taptap.core.domain.repository.WelcomeRepository
import com.compose.taptap.core.network.di.TapTapAppDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun repoModule() = module {
    single<LocalStorage> { MMKVStorageImpl(get()) }
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
    single<SearchRepository> { SearchRepositoryImpl(get()) }
    single<MeRepository> {
        MeRepositoryImpl(
            tapTapService = get(),
            dispatcher = get<CoroutineDispatcher>(named(TapTapAppDispatcher.IO))
        )
    }
}
