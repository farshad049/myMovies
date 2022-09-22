package com.example.moviesapp.model.ui

import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.model.network.GenresModel

data class UiMovieAndGenre(
    val movie: List<DomainMovieModel?>,
    val genre: List<GenresModel?>
)
