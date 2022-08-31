package com.example.mystore.redux




data class ApplicationState(
    val movieFilterByGenre : FilterByGenreInfo = FilterByGenreInfo(),
    val movieFilterByImdb : FilterByImdbInfo = FilterByImdbInfo()
)

{
    data class FilterByGenreInfo(
        val genres : Set<String> = setOf("Crime","Drama","Action","Biography","History","Adventure","Fantasy","Western","Comedy","Sci-Fi",
            "Mystery","Thriller","Family","War","Animation","Romance","Horror","Music","Film-Noir","Musical","Sport"),
        val selectedGenres : Set<String> = emptySet()
    )

    data class FilterByImdbInfo(
        val imdbRate : Set<Double> = setOf( 9.0 , 8.5, 8.0 ),
        val selectedImdbRate : Set<Double> = emptySet()
    )
}


