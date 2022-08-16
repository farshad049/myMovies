package com.example.moviesapp.epoxy


interface OnClickInterface {
    fun onMovieClick(movieId: Int)
    fun onGenreClick(genreId: Int,genreName:String)
}