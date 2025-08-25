package com.seno.home.presentation.detail

import com.seno.core.presentation.utils.UiText
import com.seno.home.domain.model.MovieDetail

data class DetailState(
    val isLoading: Boolean = true,
    val detailMovie: MovieDetail? = null,
    val error: UiText? = null
)