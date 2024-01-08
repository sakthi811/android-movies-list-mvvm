package com.droiddude.apps.mvvmmovies.domain.repository

import com.droiddude.apps.mvvmmovies.domain.model.Movie
import com.droiddude.apps.mvvmmovies.utils.MovieResource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    suspend fun getMovieList(
        shouldFetchFromRemote: Boolean,
        category: String,
        page: Int
    ) : Flow<MovieResource<List<Movie>>>

    suspend fun getMovie(id : Int) : Flow<MovieResource<Movie>>
}