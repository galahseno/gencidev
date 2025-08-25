package com.seno.home.presentation.detail.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun MetaRow(
    releaseDate: String?,
    voteAverage: Double,
    runtime: Int?
) {
    val parts = buildList {
        releaseDate?.takeIf { it.isNotBlank() }?.let { add(it) }
        add("★ ${"%.1f".format(voteAverage)}")
        runtime?.let { add("${it}m") }
    }.joinToString("   •   ")

    if (parts.isNotBlank()) {
        Text(
            text = parts,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}