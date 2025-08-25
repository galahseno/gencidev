package com.seno.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seno.core.data.database.converter.MovieDetailConverters
import com.seno.core.data.database.dao.MovieDao
import com.seno.core.data.database.dao.MovieDetailDao
import com.seno.core.data.database.dao.RemoteKeyDao
import com.seno.core.data.database.entity.MovieDetailEntity
import com.seno.core.data.database.entity.MovieEntity
import com.seno.core.data.database.entity.RemoteKeyEntity

@Database(
    version = 1,
    entities = [
        MovieEntity::class,
        RemoteKeyEntity::class,
        MovieDetailEntity::class
    ],
    exportSchema = false
)
@TypeConverters(MovieDetailConverters::class)
abstract class MovieDb : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeyDao
    abstract fun movieDetailDao(): MovieDetailDao
}