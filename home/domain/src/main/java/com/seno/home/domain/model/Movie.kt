package com.seno.home.domain.model

data class Movie(
    val id: Int,
    val title: String = "",
    val overview: String = "",
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double = .0
)