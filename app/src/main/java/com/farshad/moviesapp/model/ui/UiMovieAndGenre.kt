package com.farshad.moviesapp.model.ui

import com.farshad.moviesapp.model.domain.DomainMovieModel
import com.farshad.moviesapp.model.network.GenresModel

data class UiMovieAndGenre(
    val movie: List<DomainMovieModel?>,
    val genre: List<GenresModel?>
)
