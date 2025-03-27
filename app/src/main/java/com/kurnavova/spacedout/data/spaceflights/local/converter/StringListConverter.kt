package com.kurnavova.spacedout.data.spaceflights.local.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

/**
 * Converts list of strings to JSON and back using Kotlinx Serialization.
 */
internal class StringListConverter {

    @TypeConverter
    fun fromList(list: List<String>?): String {
        return Json.encodeToString(list ?: emptyList())
    }

    @TypeConverter
    fun toList(data: String?): List<String> {
        return if (data.isNullOrEmpty()) emptyList() else Json.decodeFromString(data)
    }
}
