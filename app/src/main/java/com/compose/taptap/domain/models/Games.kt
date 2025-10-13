package com.compose.taptap.domain.models

import com.compose.taptap.data.network.models.App
import com.compose.taptap.data.network.models.Category
import com.compose.taptap.data.network.models.Dailies
import com.compose.taptap.data.network.models.RecReason


data class Games(
    val type: String?,
    val identification: String?,
    val app: App?,
    val recReason: RecReason?,
    val category: Category?,
    val dailies: Dailies?
)