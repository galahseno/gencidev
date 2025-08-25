package com.seno.home.data.mapper

import com.seno.core.data.database.entity.MovieDetailEntity
import com.seno.core.data.database.entity.MovieEntity
import com.seno.core.data.database.entity.StoredGenre
import com.seno.home.data.dto.GenreDto
import com.seno.home.data.dto.MovieDetailDto
import com.seno.home.data.dto.MovieDto
import com.seno.home.domain.model.Genre
import com.seno.home.domain.model.Movie
import com.seno.home.domain.model.MovieDetail

fun MovieEntity.toMovie(): Movie {
    return Movie(
       id = id,
       title = title,
       overview = overview,
       posterPath = posterPath,
       releaseDate = releaseDate,
       voteAverage = voteAverage
    )
}

fun MovieDto.toEntity() = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)

fun MovieDetailDto.toMovieDetail(): MovieDetail =
    MovieDetail(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        runtime = runtime,
        tagline = tagline,
        homepage = homepage,
        genres = genres.map { it.toGenre() }
    )

fun GenreDto.toGenre(): Genre =
    Genre(
        id = id,
        name = name
    )


fun MovieDetailDto.toEntity() = MovieDetailEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    runtime = runtime,
    tagline = tagline,
    homepage = homepage,
    genres = genres.map { it.toStored() }
)

private fun GenreDto.toStored() = StoredGenre(id = id, name = name)

fun MovieDetailEntity.toDomain() = MovieDetail(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    runtime = runtime,
    tagline = tagline,
    homepage = homepage,
    genres = genres.map { it.toDomain() }
)

private fun StoredGenre.toDomain() = Genre(id = id, name = name)
