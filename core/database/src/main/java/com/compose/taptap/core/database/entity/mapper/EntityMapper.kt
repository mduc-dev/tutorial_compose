package com.compose.taptap.core.database.entity.mapper

/**
 * Created by duc on 20/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

interface EntityMapper<Domain, Entity> {
    fun asEntity(entity: Entity): Entity

    fun asDomain(domain: Domain): Domain
}