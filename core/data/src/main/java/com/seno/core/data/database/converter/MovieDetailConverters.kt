package com.seno.core.data.database.converter

import androidx.room.TypeConverter
import com.seno.core.data.database.entity.StoredGenre
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

object MovieDetailConverters {

    @Serializable
    private data class SGenre(val id: Int, val name: String)

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    @JvmStatic
    fun genresToString(list: List<StoredGenre>?): String {
        val wire = list?.map { SGenre(it.id, it.name) } ?: emptyList()
        return json.encodeToString(wire)
    }

    @TypeConverter
    @JvmStatic
    fun stringToGenres(raw: String?): List<StoredGenre> {
        if (raw.isNullOrBlank()) return emptyList()
        return json.decodeFromString<List<SGenre>>(raw).map { StoredGenre(it.id, it.name) }
    }
}
