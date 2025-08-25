package com.seno.home.presentation.detail

sealed interface DetailAction {
    data object Retry : DetailAction
    data object Back : DetailAction
}