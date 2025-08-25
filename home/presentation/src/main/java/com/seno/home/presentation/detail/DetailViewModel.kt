package com.seno.home.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seno.core.domain.onError
import com.seno.core.domain.onSuccess
import com.seno.core.presentation.utils.UiText
import com.seno.core.presentation.utils.asUiText
import com.seno.home.domain.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    private val movieId: Int = savedStateHandle["movieId"] ?: -1

    init {
        fetch()
    }

    private fun fetch() {
        if (movieId == -1) {
            _state.value = _state.value.copy(
                isLoading = false,
                error = UiText.DynamicString("Invalid movie id")
            )
            return
        }
        _state.value = _state.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            movieRepository
                .getDetailMovie(movieId)
                .onError { e ->
                    _state.value =
                        _state.value.copy(
                            isLoading = false,
                            error = e.asUiText()
                        )
                }
                .onSuccess { detail ->
                    _state.value =
                        _state.value.copy(isLoading = false, detailMovie = detail, error = null)
                }
        }
    }

    fun onAction(action: DetailAction) {
        when (action) {
            DetailAction.Retry -> fetch()
            else -> Unit
        }
    }
}