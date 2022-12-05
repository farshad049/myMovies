package com.farshad.moviesapp.ui.filter.model

data class FilterByGenreInfo (
        val genres: Set<String> = setOf(),
        var selectedGenres: Set<String> = emptySet()
)