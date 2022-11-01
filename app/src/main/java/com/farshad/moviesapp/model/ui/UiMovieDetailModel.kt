package com.farshad.moviesapp.model.ui

import com.farshad.moviesapp.model.domain.DomainMovieModel

data class UiMovieDetailModel (
    val movie : DomainMovieModel ,
    val isFavorite : Boolean
        )