package com.compose.taptap.core.data.datasource.local

interface LocalStorage {
    fun putString(key: String, value: String)
    fun getString(key: String, default: String? = null): String?
    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, default: Boolean = false): Boolean
    fun remove(key: String)
    fun clear()
    fun contains(key: String): Boolean
}
