package com.example.moviesapp.model.ui

import com.example.moviesapp.model.domain.DomainMovieModel

data class UiMovieDetailModel (
    val movie : DomainMovieModel ,
    val isFavorite : Boolean
        )