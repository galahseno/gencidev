package com.seno.home.presentation.home

sealed interface HomeAction {
    data class QueryChanged(val value: String) : HomeAction
    data class SubmitSearch(val query: String) : HomeAction
    data class ClickMovie(val id: Int) : HomeAction
}