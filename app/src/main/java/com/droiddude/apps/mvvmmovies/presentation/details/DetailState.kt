package com.droiddude.apps.mvvmmovies.presentation.details

import com.droiddude.apps.mvvmmovies.domain.model.Movie

data class DetailState(
    val isLoading : Boolean = true,
    val movie : Movie? = null
)
