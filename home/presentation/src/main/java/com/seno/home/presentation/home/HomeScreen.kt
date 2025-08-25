@file:OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)

package com.seno.home.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.seno.home.domain.model.Movie
import com.seno.home.presentation.home.components.MovieList
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoot(
    onClickMovie: (movieId: Int) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val moviePaging = viewModel.story.collectAsLazyPagingItems()
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        moviePaging = moviePaging,
        onAction = { action ->
            when (action) {
                is HomeAction.ClickMovie -> onClickMovie(action.id)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    moviePaging: LazyPagingItems<Movie>,
    onAction: (HomeAction) -> Unit,
) {
    val focus = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movies") }
            )
        }
    ) { innerPadding ->
        val controller = LocalSoftwareKeyboardController.current

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = state.query,
                onValueChange = { onAction(HomeAction.QueryChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search movie…") },
                singleLine = true,
                trailingIcon = {
                    if (state.query.isNotBlank()) {
                        TextButton(
                            onClick = {
                                onAction(HomeAction.QueryChanged(""))
                                focus.clearFocus()
                            }
                        ) {
                            Text("Clear")
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focus.clearFocus()
                        controller?.hide()
                        onAction(HomeAction.SubmitSearch(state.query))
                    }
                )
            )

            MovieList(
                movies = moviePaging,
                onRetry = { moviePaging.retry() },
                onClick = { onAction(HomeAction.ClickMovie(it)) }
            )
        }
    }
}