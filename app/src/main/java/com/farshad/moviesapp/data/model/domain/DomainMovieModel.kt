package com.farshad.moviesapp.data.model.domain

data class DomainMovieModel(
val actors: String = "",
val country: String = "",
val director: String = "",
val genres: List<String> = listOf(),
val id: Int = 0,
val images: List<String> = listOf(),
val imdb_rating: String = "",
val plot: String = "",
val poster: String = "",
val rated: String = "",
val title: String = "",
val year: String = ""
)

