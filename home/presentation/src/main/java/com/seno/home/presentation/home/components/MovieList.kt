package com.seno.home.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.seno.home.domain.model.Movie

@Composable
internal fun MovieList(
    movies: LazyPagingItems<Movie>,
    onRetry: () -> Unit,
    onClick: (Int) -> Unit
) {
    val isRefreshing = movies.loadState.refresh is LoadState.Loading
    val refreshError = movies.loadState.refresh as? LoadState.Error
    val hasItems = movies.itemCount > 0

    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    LaunchedEffect(movies.loadState.refresh) {
        if (movies.loadState.refresh is LoadState.Loading) {
            listState.animateScrollToItem(0)
        }
    }

    when {
        refreshError != null && !hasItems -> {
            ErrorState(
                message = refreshError.error.message ?: "Failed to load",
                onRetry = onRetry
            )
        }

        isRefreshing && !hasItems -> {
            Box(
                Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {
            if (movies.itemCount == 0 &&
                movies.loadState.refresh is LoadState.NotLoading &&
                movies.loadState.append.endOfPaginationReached
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "No results found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = movies.itemCount,
                        key = movies.itemKey { it.id }
                    ) { index ->
                        movies[index]?.let { movie ->
                            MovieRow(movie = movie, onClick = { onClick(movie.id) })
                        }
                    }

                    item(key = "append_state") {
                        when (val append = movies.loadState.append) {
                            is LoadState.Loading -> {
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) { CircularProgressIndicator() }
                            }

                            is LoadState.Error -> {
                                ErrorState(
                                    message = append.error.message ?: "Failed to load more",
                                    onRetry = onRetry
                                )
                            }

                            else -> Unit
                        }
                    }
                }
            }
        }
    }
}
