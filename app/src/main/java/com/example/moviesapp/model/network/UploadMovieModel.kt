package com.example.moviesapp.model.network

data class UploadMovieModel(
    val country: String,
    val director: String = "",
    val id: Int = 0,
    val imdb_id: String,
    val imdb_rating: String = "",
    val imdb_votes: String = "",
    val poster: String = "",
    val title: String,
    val year: Int
)