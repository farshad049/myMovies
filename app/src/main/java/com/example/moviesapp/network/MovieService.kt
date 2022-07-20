package com.example.moviesapp.network

import com.example.moviesapp.model.network.NetworkMovieModel
import com.example.moviesapp.model.network.PagingModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movies?")
    suspend fun getMoviesPage(@Query("page") pageIndex:Int): Response<PagingModel>

    @GET("movies/{movie-id}")
    suspend fun getSingleMovie(@Path("movie-id") movieId:Int):Response<NetworkMovieModel>



}