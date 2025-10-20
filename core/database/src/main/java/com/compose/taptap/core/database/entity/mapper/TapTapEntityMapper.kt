package com.compose.taptap.core.database.entity.mapper

import com.compose.taptap.core.database.entity.TapTapEntity
/**
 * Created by duc on 20/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

//TODO: write exactly data type of game here
object TapTapEntityMapper : EntityMapper<List<Any>, List<TapTapEntity>> {
    override fun asEntity(entity: List<TapTapEntity>): List<TapTapEntity> {
        TODO("Not yet implemented")
    }

    override fun asDomain(domain: List<Any>): List<Any> {
        TODO("Not yet implemented")
    }

}