package com.compose.taptap.core.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

@ProvidedTypeConverter
class StringListConverter(private val json: Json) {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.let { json.decodeFromString<List<String>>(it) }
    }

    @TypeConverter
    fun toString(value: List<String>?): String? {
        return value?.let { json.encodeToString(it) }
    }
}