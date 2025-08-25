package com.seno.core.data.di

import androidx.room.Room
import com.seno.core.data.database.MovieDb
import com.seno.core.data.network.HttpClientFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val coreDataModule = module {
    single { HttpClientFactory().build() }

    single {

        Room.databaseBuilder(
            androidApplication(),
            MovieDb::class.java,
            "movie.db"
        )
            .build()
    }
    single { get<MovieDb>().movieDao() }
    single { get<MovieDb>().remoteKeysDao() }
}