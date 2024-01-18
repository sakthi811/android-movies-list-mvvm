package com.droiddude.apps.mvvmmovies.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddude.apps.mvvmmovies.domain.repository.MovieListRepository
import com.droiddude.apps.mvvmmovies.utils.MovieResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository : MovieListRepository,
    private val savedStateHandle : SavedStateHandle
) : ViewModel() {

    private val movieId = savedStateHandle.get<Int>("movieId")

    private var _detailState = MutableStateFlow(DetailState())
    val detailState = _detailState.asStateFlow()

    init {
        getMovie(movieId ?: -1)
    }

    private fun getMovie(movieId : Int) {
        viewModelScope.launch {
            _detailState.update {
                it.copy(
                    isLoading = true
                )
            }

            repository.getMovie(movieId).collectLatest { result ->
                when(result) {
                    is MovieResource.Error -> {
                        _detailState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                    is MovieResource.Loading -> {
                        _detailState.update {
                            it.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                    is MovieResource.Success -> {
                        result.data.let { movie ->
                            _detailState.update {
                                it.copy(
                                    movie = movie
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}