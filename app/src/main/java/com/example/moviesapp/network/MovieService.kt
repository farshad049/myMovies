package com.example.moviesapp.network

import com.example.moviesapp.model.network.NetworkMovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {
    @GET("movies")
    suspend fun getAllMovies(): Response<List<NetworkMovieModel>>

    @GET("movies/{movie-id}")
    suspend fun getSingleMovie(@Path("movie-id") movieId:Int):Response<NetworkMovieModel>



}