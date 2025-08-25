@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.seno.home.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.seno.home.domain.MovieRepository
import com.seno.home.domain.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState(
        query = savedStateHandle[QUERY] ?: ""
    ))
    val state = _state.asStateFlow()

    val story: Flow<PagingData<Movie>> = _state
        .map { it.query.trim() }
        .distinctUntilChanged()
        .debounce(700)
        .flatMapLatest {
            movieRepository
                .getMovies(
                    query = it.takeIf { it.isNotEmpty() }
                )
        }
        .cachedIn(viewModelScope)

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.QueryChanged -> {
                _state.update {
                    it.copy(query = action.value)
                }
                savedStateHandle[QUERY] = action.value
            }

            is HomeAction.SubmitSearch -> {
                _state.update {
                    it.copy(query = action.query)
                }
            }

            else -> Unit
        }
    }

    override fun onCleared() {
        super.onCleared()
        savedStateHandle[QUERY] = ""
    }

    companion object{
        private const val QUERY = "query"
    }
}