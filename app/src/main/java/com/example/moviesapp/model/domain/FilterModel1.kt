package com.example.moviesapp.model.domain

data class FilterModel1 (
        val genres: Set<String> = setOf("Crime","Drama","Action","Biography","History","Adventure","Fantasy","Western","Comedy","Sci-Fi",
                "Mystery","Thriller","Family","War","Animation","Romance","Horror","Music","Film-Noir","Musical","Sport"),
        var selectedGenres: Set<String> = emptySet()
)