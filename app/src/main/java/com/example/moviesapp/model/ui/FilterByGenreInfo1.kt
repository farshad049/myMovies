package com.example.moviesapp.model.ui

data class FilterByGenreInfo1 (
        val genres: Set<String> = setOf(),
        var selectedGenres: Set<String> = emptySet()
)