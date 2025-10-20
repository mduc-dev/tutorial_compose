package com.compose.taptap.core.data.repository.game

import androidx.annotation.WorkerThread
import com.compose.taptap.core.data.model.Games
import com.compose.taptap.core.database.TapTapDao
import com.compose.taptap.core.model.ListGameItem
import com.compose.taptap.core.network.di.TapTapAppDispatcher
import com.compose.taptap.core.network.service.TapTapClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GamesRepositoryImpl(
    private val taptapClient: TapTapClient,
    private val taptapDao: TapTapDao,
    private val dispatcher: TapTapAppDispatcher
) : GamesRepository {

    @WorkerThread
    override fun getCachedGames(): Flow<Result<List<ListGameItem>>> = flow {

    }

    override fun refreshGames(): Flow<Result<Games>> = flow {

    }

    override fun getGames(): Flow<Result<List<ListGameItem>>> = flow {

    }
}


private fun ListGameItem.toDomain(): ListGameItem = ListGameItem(
    type = type,
    identification = identification,
    app = app,
    recReason = recReason,
    category = category,
    dailies = dailies
)
