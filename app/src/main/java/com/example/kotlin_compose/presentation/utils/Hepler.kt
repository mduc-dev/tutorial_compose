package com.example.kotlin_compose.presentation.utils


fun isEmpty(value: Any?): Boolean {
    if (value == null) return true
    return when (value) {
        is String -> value.isEmpty()
        is Collection<*> -> value.isEmpty()
        else -> false
    }
}