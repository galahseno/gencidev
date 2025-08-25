package com.seno.home.data.remote

import com.seno.core.data.network.get
import com.seno.core.domain.DataError
import com.seno.core.domain.Result
import com.seno.home.data.dto.MovieDetailDto
import com.seno.home.data.dto.MovieListDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MovieApi(
    private val client: HttpClient,
) {
    suspend fun popular(page: Int): MovieListDto {
        return client.get("movie/popular") {
            parameter("page", page)
        }.body()
    }

    suspend fun search(query: String, page: Int): MovieListDto {
        return client.get("search/movie") {
            parameter("query", query)
            parameter("page", page)
        }.body()
    }

    suspend fun detail(id: Int): Result<MovieDetailDto, DataError.Network> {
        return client.get<MovieDetailDto>(
            route = "movie/$id"
        )
    }
}
