@file:OptIn(ExperimentalPagingApi::class)

package com.seno.home.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.seno.core.data.database.MovieDb
import com.seno.core.domain.DataError
import com.seno.core.domain.Result
import com.seno.home.data.mapper.toDomain
import com.seno.home.data.mapper.toEntity
import com.seno.home.data.mapper.toMovie
import com.seno.home.data.mapper.toMovieDetail
import com.seno.home.data.remote.MovieApi
import com.seno.home.domain.MovieRepository
import com.seno.home.domain.model.Movie
import com.seno.home.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val movieDb: MovieDb,
    private val movieApi: MovieApi
) : MovieRepository {
    override fun getMovies(query: String?): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 10,
                enablePlaceholders = false
            ),
            remoteMediator = MovieRemoteMediator(
                query = query,
                movieDb = movieDb,
                api = movieApi,
            ),
            pagingSourceFactory = {
                movieDb.movieDao().getAllStories()
            }
        ).flow.map { pagingData ->
            pagingData.map { movie ->
                movie.toMovie()
            }
        }
    }

    override suspend fun getDetailMovie(movieId: Int): Result<MovieDetail, DataError.Network> {
        movieDb.movieDetailDao().getDetail(movieId)?.let { cached ->
            return Result.Success(cached.toDomain())
        }

        return when (val movieDetail = movieApi.detail(movieId)) {
            is Result.Error -> Result.Error(movieDetail.error)

            is Result.Success -> {
                val entity = movieDetail.data.toEntity()
                movieDb.movieDetailDao().upsert(entity)
                Result.Success(movieDetail.data.toMovieDetail())
            }
        }
    }
}