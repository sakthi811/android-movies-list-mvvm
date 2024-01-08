package com.droiddude.apps.mvvmmovies.presentation

import com.droiddude.apps.mvvmmovies.domain.model.Movie

data class MovieListState(
    val isLoading : Boolean = false,
    val popularMovieListPage : Int = 1,
    val upcomingMovieListPage : Int = 1,
    val isCurrentPopularScreen : Boolean = false,
    val popularMovieList : List<Movie> = emptyList(),
    val upcomingMovieList : List<Movie> = emptyList()
)
