package com.compose.taptap.core.data.model

import com.compose.taptap.core.model.App
import com.compose.taptap.core.model.Category
import com.compose.taptap.core.model.Dailies
import com.compose.taptap.core.model.RecReason


data class Games(
    val type: String?,
    val identification: String?,
    val app: App?,
    val recReason: RecReason?,
    val category: Category?,
    val dailies: Dailies?
)