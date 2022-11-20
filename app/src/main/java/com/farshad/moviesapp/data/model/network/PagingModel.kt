package com.farshad.moviesapp.data.model.network

data class PagingModel(
    val data: List<NetworkMovieModel> = listOf(),
    val metadata: Metadata = Metadata()
)