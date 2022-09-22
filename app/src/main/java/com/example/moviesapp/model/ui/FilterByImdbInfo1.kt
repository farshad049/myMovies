package com.example.moviesapp.model.ui

data class FilterByImdbInfo1(
   val imdbRate : Set<Double>  = setOf(),
   val selectedImdbRate : Set<Double> = emptySet()
)
