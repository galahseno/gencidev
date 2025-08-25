package com.seno.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seno.core.data.database.entity.MovieDetailEntity

@Dao
interface MovieDetailDao {
    @Query("SELECT * FROM movie_detail WHERE id = :id LIMIT 1")
    suspend fun getDetail(id: Int): MovieDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(detail: MovieDetailEntity)
}
