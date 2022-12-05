package com.farshad.moviesapp.ui.dashboard.model

import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.data.model.network.GenresModel

data class UiMovieAndGenre(
    val movie: List<DomainMovieModel?>,
    val genre: List<GenresModel?>
)
