package com.droiddude.apps.mvvmmovies.data.repository

import com.droiddude.apps.mvvmmovies.data.local.MovieDatabase
import com.droiddude.apps.mvvmmovies.data.remote.MovieApi
import com.droiddude.apps.mvvmmovies.data.toMovie
import com.droiddude.apps.mvvmmovies.data.toMovieEntity
import com.droiddude.apps.mvvmmovies.domain.model.Movie
import com.droiddude.apps.mvvmmovies.domain.repository.MovieListRepository
import com.droiddude.apps.mvvmmovies.utils.MovieResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi : MovieApi,
    private val movieDb : MovieDatabase
) : MovieListRepository {
    override suspend fun getMovieList(
        shouldFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<MovieResource<List<Movie>>> {
        return flow {
            emit(MovieResource.Loading(true))

            val movieListFromDb = movieDb.movieDao.getMovieListByCategory(category)
            val shouldLoadMovieFromDb = movieListFromDb.isNotEmpty() && !shouldFetchFromRemote

            if(shouldLoadMovieFromDb) {
                emit(MovieResource.Success(
                    data = movieListFromDb.map { movieEntity ->
                        movieEntity.toMovie(category)
                    }
                ))
                emit(MovieResource.Loading(false))
                return@flow
            }

            val movieListFromApi = try {
                movieApi.getMoviesList(category, page)
            } catch(e : Exception) {
                e.printStackTrace()
                emit(MovieResource.Error(message = "Error Loading Movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(MovieResource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(MovieResource.Error(message = "Error loading movies"))
                return@flow
            }

            val movieEntities = movieListFromApi.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }

            movieDb.movieDao.upsertMovieList(movieEntities)

            emit(MovieResource.Success(
                movieEntities.map { movieEntity ->
                    movieEntity.toMovie(category)
                }
            ))

            emit(MovieResource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<MovieResource<Movie>> {
        return flow {
            emit(MovieResource.Loading(true))

            val movieEntity = movieDb.movieDao.getMovieById(id)

            if(movieEntity != null) {
                emit(MovieResource.Success(
                    movieEntity.toMovie(category = movieEntity.category)
                ))
                emit(MovieResource.Loading(false))
                return@flow
            }

            emit(MovieResource.Error("No such Movie"))
            emit(MovieResource.Loading(false))
        }
    }
}