@file:OptIn(ExperimentalPagingApi::class)

package com.seno.home.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.seno.core.data.database.MovieDb
import com.seno.core.data.database.entity.MovieEntity
import com.seno.core.data.database.entity.RemoteKeyEntity
import com.seno.home.data.mapper.toEntity
import com.seno.home.data.remote.MovieApi

class MovieRemoteMediator(
    private val query: String?,
    private val movieDb: MovieDb,
    private val api: MovieApi
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun initialize(): InitializeAction =
        InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: START_PAGE
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        return try {
            val response = if (!query.isNullOrEmpty()) api.search(query, page) else api.popular(page)

            val movies = response.results.map { it.toEntity() }
            val endOfPaginationReached =
                movies.isEmpty() || page >= response.totalPages

            movieDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDb.remoteKeysDao().deleteAll()
                    movieDb.movieDao().deleteAll()
                }
                val prevKey = if (page == START_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = movies.map {
                    RemoteKeyEntity(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }

                movieDb.remoteKeysDao().upsertAll(keys)
                movieDb.movieDao().upsertAll(movies.onEachIndexed { _, movie -> movie.page = page })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            if (loadType == LoadType.REFRESH) {
                val hasCache = movieDb.movieDao().countAll() > 0
                if (hasCache) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }
            }
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { movieDb.remoteKeysDao().getRemoteKeyId(it.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { movieDb.remoteKeysDao().getRemoteKeyId(it.id) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeyEntity? {
        return state.anchorPosition?.let { pos ->
            state.closestItemToPosition(pos)?.id?.let { id ->
                movieDb.remoteKeysDao().getRemoteKeyId(id)
            }
        }
    }

    private companion object Companion {
        const val START_PAGE = 1
    }
}