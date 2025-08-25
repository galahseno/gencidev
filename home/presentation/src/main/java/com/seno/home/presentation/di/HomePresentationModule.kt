package com.seno.home.presentation.di

import com.seno.home.presentation.detail.DetailViewModel
import com.seno.home.presentation.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homePresentationModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
}