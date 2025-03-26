package com.kurnavova.spacedout.data.spaceflights.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Converts list of strings to JSON and back.
 */
internal class StringListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<String>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(data: String?): List<String> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }
}
