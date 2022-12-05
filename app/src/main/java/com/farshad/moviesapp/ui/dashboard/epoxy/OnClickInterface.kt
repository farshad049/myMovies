package com.farshad.moviesapp.ui.dashboard.epoxy


interface OnClickInterface {
    fun onMovieClick(movieId: Int)
    fun onGenreClick(genreId: Int,genreName:String)
}