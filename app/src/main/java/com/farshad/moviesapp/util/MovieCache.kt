package com.farshad.moviesapp.util

import com.farshad.moviesapp.data.model.domain.DomainMovieModel

object MovieCache {
    val movieMap= mutableMapOf<Int,DomainMovieModel>()

    val similarMovieMap= mutableMapOf<Int,List<DomainMovieModel>>()
}