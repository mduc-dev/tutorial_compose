package com.compose.taptap.core.database.entity.mapper

/**
 * Created by duc on 20/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

interface EntityMapper<Domain, Entity> {
    fun asEntity(domain: Domain): Entity

    fun asDomain(entity: Entity): Domain
}
