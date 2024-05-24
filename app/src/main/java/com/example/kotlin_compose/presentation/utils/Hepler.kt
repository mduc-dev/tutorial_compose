package com.example.kotlin_compose.presentation.utils

import android.util.Log
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class DisabledInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}

fun isEmpty(value: Any?): Boolean {
    val identifier = "[IsEmpty Checking]"
    Log.i(
        identifier,
        "${DebuggingIdentifiers.actionOrEventSucceded} value: $value is empty",
    )
    if (value == null) return true
    return when (value) {
        is String -> value.isEmpty()
        is Collection<*> -> value.isEmpty()
        else -> false
    }
}