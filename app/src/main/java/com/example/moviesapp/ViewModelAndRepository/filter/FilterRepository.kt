package com.example.moviesapp.ViewModelAndRepository.filter

import com.example.moviesapp.model.ui.FilterByGenreInfo1
import com.example.moviesapp.model.ui.FilterByImdbInfo1
import javax.inject.Inject


class FilterRepository @Inject constructor() {
    var selectedGenreee = setOf<String>()

    suspend fun getFilterByGenreInfo(): FilterByGenreInfo1 {
        var genre = setOf("Crime","Drama","Action","Biography","History","Adventure","Fantasy","Western","Comedy","Sci-Fi",
            "Mystery","Thriller","Family","War","Animation","Romance","Horror","Music","Film-Noir","Musical","Sport")


       return FilterByGenreInfo1(
           genres = genre ,
           selectedGenres = selectedGenreee
       )
    }

    suspend fun getFilterByImdbRateInfo(): FilterByImdbInfo1 {
        var imdbRate = setOf( 9.0 , 8.5, 8.0 )
        val selectedImdbRate = setOf<Double>()

        return FilterByImdbInfo1(
            imdbRate = imdbRate,
            selectedImdbRate = selectedImdbRate
        )
    }




}