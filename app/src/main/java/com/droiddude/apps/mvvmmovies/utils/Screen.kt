package com.droiddude.apps.mvvmmovies.utils

sealed class Screen(val route : String) {

    object Home : Screen("home")
    object PopularMovies : Screen("popularMovies")
    object UpcomingMovies : Screen("upcomingMovies")
    object Details : Screen("details")
}
