package com.example.kotlin_compose.domain.models

import com.example.kotlin_compose.data.network.models.App
import com.example.kotlin_compose.data.network.models.Category
import com.example.kotlin_compose.data.network.models.RecReason


data class Games(
    val type: String?,
    val identification: String?,
    val app: App?,
    val recReason: RecReason?,
    val category: Category?,
)