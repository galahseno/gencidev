package com.seno.home.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double = 0.0,
    val runtime: Int? = null,
    val tagline: String? = null,
    val homepage: String? = null,
    val genres: List<Genre> = emptyList()
)

data class Genre(
    val id: Int,
    val name: String
)