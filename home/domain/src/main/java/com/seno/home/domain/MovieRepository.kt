package com.seno.home.domain

import androidx.paging.PagingData
import com.seno.core.domain.DataError
import com.seno.core.domain.Result
import com.seno.home.domain.model.Movie
import com.seno.home.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(query: String? = null): Flow<PagingData<Movie>>
    suspend fun getDetailMovie(movieId: Int): Result<MovieDetail, DataError.Network>
}