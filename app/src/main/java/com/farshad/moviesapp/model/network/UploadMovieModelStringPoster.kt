package com.farshad.moviesapp.model.network

data class UploadMovieModelStringPoster(
    val country: String,
    val director: String = "",
    val id: Int = 0,
    var imdb_id: String,
    val imdb_rating: String = "",
    val imdb_votes: String = "",
    val poster: String = "",
    var title: String,
    val year: Int
)