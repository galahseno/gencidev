package com.seno.core.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.seno.core.data.database.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("Select * From movies_popular Order By page")
    fun getAllStories(): PagingSource<Int, MovieEntity>

    @Upsert
    suspend fun upsertAll(stories: List<MovieEntity>)

    @Query("SELECT COUNT(*) FROM movies_popular")
    suspend fun countAll(): Int

    @Query("DELETE FROM movies_popular")
    suspend fun deleteAll()
}