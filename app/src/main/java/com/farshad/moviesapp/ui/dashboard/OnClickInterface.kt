package com.farshad.moviesapp.ui.dashboard


interface OnClickInterface {
    fun onMovieClick(movieId: Int)
    fun onGenreClick(genreId: Int,genreName:String)
}