package com.example.moviesapp.model.ui

data class FilterByGenreAndImdbRate(
    val filteredByGenreList : List<UiGenreFilter>,
    val filteredByImdbList : List<UiImdbRateFilter>
)
