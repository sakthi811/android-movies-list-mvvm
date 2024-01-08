package com.droiddude.apps.mvvmmovies.presentation

sealed interface MovieListUiEvent {
    data class Paginate(val category : String) : MovieListUiEvent
    object Navigate : MovieListUiEvent
}