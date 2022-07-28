package com.example.moviesapp.network

import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.model.network.NetworkMovieModel
import com.example.moviesapp.model.network.PagingModel
import com.example.moviesapp.model.network.UploadMovieModel
import retrofit2.Response
import retrofit2.http.*

interface MovieService {
    @GET("movies?")
    suspend fun getMoviesPage(@Query("page") pageIndex:Int): Response<PagingModel>

    @GET("movies/{movie-id}")
    suspend fun getSingleMovie(@Path("movie-id") movieId:Int):Response<NetworkMovieModel>

    @GET("genres/{genre-id}/movies?page")
    suspend fun getMovieByGenre(@Path("genre-id") genreId:Int): Response<PagingModel>

    @GET("movies?")
    suspend fun getMoviesPageByName(@Query("q") movieName:String, @Query("page") pageIndex:Int): Response<PagingModel>

    @POST("movies")
    suspend fun pushMovies(@Body movie:UploadMovieModel): Response<UploadMovieModel>





}