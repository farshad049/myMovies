package com.example.moviesapp.network

import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.model.network.NetworkMovieModel
import com.example.moviesapp.model.network.PagingModel
import com.example.moviesapp.model.network.UploadMovieModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @Multipart
    @POST("movies")
    suspend fun pushMoviesMulti(@Part ("poster") poster:MultipartBody.Part ,
                                @Part ("title") title:RequestBody,
                                @Part ("imdb_id") imdb_id:RequestBody,
                                @Part ("country") country:RequestBody,
                                @Part ("year") year:RequestBody
    ): Response<UploadMovieModel>





}