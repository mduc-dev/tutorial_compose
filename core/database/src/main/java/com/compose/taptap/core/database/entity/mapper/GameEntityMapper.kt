package com.compose.taptap.core.database.entity.mapper

import com.compose.taptap.core.database.entity.GameEntity
import com.compose.taptap.core.model.ListGameItem

/**
 * Created by duc on 20/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

//TODO: write exactly data type of game here
object GameEntityMapper : EntityMapper<List<ListGameItem>, List<GameEntity>> {
    override fun asEntity(domain: List<ListGameItem>): List<GameEntity> {
        return domain.mapNotNull { game ->
            val app = game.app ?: return@mapNotNull null
            val identifier = app.identifier

            GameEntity(
                identifier = identifier,
                title = app.title,
                iconUrl = app.icon?.mediumUrl,
                releasedTime = app.releasedTime,
                ratingScore = app.stat?.rating?.score
            )
        }
    }

    override fun asDomain(entity: List<GameEntity>): List<ListGameItem> {
       return entity.map { gameEntity ->
           ListGameItem(
               type = null,
               identification = gameEntity.identifier,
               app = null,
               recReason = null,
               category = null,
               dailies = null
           )
       }
    }

}


fun List<ListGameItem>.asEntity(): List<GameEntity> {
    return GameEntityMapper.asEntity(this)
}

fun List<GameEntity>?.asDomain(): List<ListGameItem> {
    return GameEntityMapper.asDomain(this.orEmpty())
}
