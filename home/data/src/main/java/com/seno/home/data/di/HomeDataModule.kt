package com.seno.home.data.di

import com.seno.home.data.MovieRepositoryImpl
import com.seno.home.data.remote.MovieApi
import com.seno.home.domain.MovieRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeDataModule = module {
    singleOf(::MovieApi)
    singleOf(::MovieRepositoryImpl) bind MovieRepository::class
}