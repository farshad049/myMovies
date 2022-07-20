package com.example.moviesapp.model.network

data class PagingModel(
    val data: List<NetworkMovieModel> = listOf(),
    val metadata: Metadata = Metadata()
)