package com.droiddude.apps.mvvmmovies.presentation

import androidx.lifecycle.ViewModel
import com.droiddude.apps.mvvmmovies.domain.repository.MovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository : MovieListRepository
) : ViewModel() {

    private var _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
    }

    fun onEvent(event : MovieListUiEvent) {
        when(event) {
            MovieListUiEvent.Navigate -> {

            }
            is MovieListUiEvent.Paginate -> {

            }
        }
    }

    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {

    }

    private fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {

    }
}