package com.seno.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_detail")
data class MovieDetailEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val runtime: Int?,
    val tagline: String?,
    val homepage: String?,
    val genres: List<StoredGenre>
)

data class StoredGenre(
    val id: Int,
    val name: String
)