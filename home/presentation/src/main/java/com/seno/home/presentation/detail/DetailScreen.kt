@file:OptIn(ExperimentalMaterial3Api::class)

package com.seno.home.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seno.core.presentation.theme.GencidevTheme
import com.seno.home.presentation.detail.components.DetailContent
import com.seno.home.presentation.detail.components.ErrorStateFull
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailRoot(
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                DetailAction.Back -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun DetailScreen(
    state: DetailState,
    onAction: (DetailAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie Detail") },
                navigationIcon = {
                    IconButton(onClick = { onAction(DetailAction.Back) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (state.error != null) {
                        IconButton(onClick = { onAction(DetailAction.Retry) }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Retry")
                        }
                    }
                }
            )
        }
    ) { inner ->
        when {
            state.isLoading -> {
                Box(
                    Modifier
                        .padding(inner)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null && state.detailMovie == null -> {
                ErrorStateFull(
                    message = state.error.asString(),
                    onRetry = { onAction(DetailAction.Retry) },
                    modifier = Modifier.padding(inner)
                )
            }

            else -> {
                state.detailMovie?.let { movie ->
                    DetailContent(
                        movie = movie,
                        modifier = Modifier
                            .padding(inner)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    GencidevTheme {
        DetailScreen(
            state = DetailState(),
            onAction = {}
        )
    }
}