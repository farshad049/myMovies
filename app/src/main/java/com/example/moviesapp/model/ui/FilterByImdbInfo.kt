package com.example.moviesapp.model.ui

data class FilterByImdbInfo(
   val imdbRate : Set<String>  = setOf(),
   val selectedImdbRate : Set<String> = emptySet()
)
