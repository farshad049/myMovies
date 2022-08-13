package com.example.moviesapp.util

import com.example.moviesapp.model.domain.DomainMovieModel

object MovieCache {
    val movieMap= mutableMapOf<Int,DomainMovieModel>()

    val similarMovieMap= mutableMapOf<Int,List<DomainMovieModel>>()
}