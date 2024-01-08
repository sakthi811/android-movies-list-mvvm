package com.droiddude.apps.mvvmmovies.di

import com.droiddude.apps.mvvmmovies.data.repository.MovieRepositoryImpl
import com.droiddude.apps.mvvmmovies.domain.repository.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieListRepository(
        movieRepositoryImpl : MovieRepositoryImpl
    ) : MovieListRepository
}