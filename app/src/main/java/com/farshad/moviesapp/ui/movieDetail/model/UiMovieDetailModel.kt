package com.farshad.moviesapp.ui.movieDetail.model

import com.farshad.moviesapp.data.model.domain.DomainMovieModel

data class UiMovieDetailModel (
    val movie : DomainMovieModel? ,
    val isFavorite : Boolean = false ,
    val similarMovies : List<DomainMovieModel> = emptyList()
        )