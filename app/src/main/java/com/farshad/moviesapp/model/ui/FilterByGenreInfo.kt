package com.farshad.moviesapp.model.ui

data class FilterByGenreInfo (
        val genres: Set<String> = setOf(),
        var selectedGenres: Set<String> = emptySet()
)