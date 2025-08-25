package com.seno.gencidev.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Home

    @Serializable
    data class Detail(
        val movieId: Int
    )
}