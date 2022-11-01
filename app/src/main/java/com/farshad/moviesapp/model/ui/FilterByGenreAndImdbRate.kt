package com.farshad.moviesapp.model.ui

data class FilterByGenreAndImdbRate(
    val filteredByGenreList : List<UiFilter>,
    val filteredByImdbList : List<UiFilter>
)
