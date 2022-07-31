package com.example.moviesapp.network

import android.media.session.MediaSession
import com.example.moviesapp.model.network.NetworkMovieModel
import com.example.moviesapp.model.network.PagingModel
import com.example.moviesapp.model.network.RegisterUserModel
import com.example.moviesapp.model.network.UploadMovieModel
import com.squareup.moshi.JsonReader
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MovieService {
    @GET("api/v1/movies?")
    suspend fun getMoviesPage(@Query("page") pageIndex:Int): Response<PagingModel>

    @GET("api/v1/movies/{movie-id}")
    suspend fun getSingleMovie(@Path("movie-id") movieId:Int):Response<NetworkMovieModel>

    @GET("api/v1/genres/{genre-id}/movies?page")
    suspend fun getMovieByGenre(@Path("genre-id") genreId:Int): Response<PagingModel>

    @GET("api/v1/movies?")
    suspend fun getMoviesPageByName(@Query("q") movieName:String, @Query("page") pageIndex:Int): Response<PagingModel>

    @POST("api/v1/movies")
    suspend fun pushMovies(@Body movie:UploadMovieModel): Response<UploadMovieModel>

    @Multipart
    @POST("api/v1/movies/multi")
    suspend fun pushMoviesMulti(@Part ("poster") poster: RequestBody,
                                @Part ("title") title:RequestBody,
                                @Part ("imdb_id") imdb_id:RequestBody,
                                @Part ("country") country:RequestBody,
                                @Part ("year") year:RequestBody
    ): Response<UploadMovieModel>

    @POST("api/v1/register/")
    suspend fun registerUser (@Body user: RegisterUserModel): Response<Any>

    @Multipart
    @POST("oauth/token")
    suspend fun loginUser (@Part ("username") username:RequestBody,
                           @Part ("password") password:RequestBody,
                           @Part("grant_type") grantType:RequestBody
    ):Response<Any>





}