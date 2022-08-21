package com.example.moviesapp.network

import android.media.session.MediaSession
import com.example.moviesapp.model.network.*
import com.squareup.moshi.JsonReader
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MovieService {
    @GET("api/v1/movies?")
    suspend fun getMoviesPaging(@Query("page") pageIndex:Int): Response<PagingModel>

    @GET("api/v1/movies?page=1")
    suspend fun getFirstPageMovie(): Response<PagingModel>

    @GET("api/v1/movies/{movie-id}")
    suspend fun getSingleMovie(@Path("movie-id") movieId:Int):Response<NetworkMovieModel>

    @GET("api/v1/genres")
    suspend fun getAllGenres():Response<List<GenresModel>>

    @GET("api/v1/genres/{genre-id}/movies?page")
    suspend fun getFirsPageMovieByGenre(@Path("genre-id") genreId:Int): Response<PagingModel>

    @GET("api/v1/genres/{genre-id}/movies?page")
    suspend fun getMovieByGenrePaging(@Path("genre-id") genreId:Int, @Query("page") pageIndex:Int): Response<PagingModel>

    @GET("api/v1/movies?")
    suspend fun getMoviesPagingByName(@Query("q") movieName:String, @Query("page") pageIndex:Int): Response<PagingModel>

    @POST("api/v1/movies")
    suspend fun pushMovies(@Body movie:UploadMovieModel): Response<UploadMovieModel>

    @Multipart
    @POST("api/v1/movies/multi")
    suspend fun pushMoviesMulti(
        @Part ("poster") poster: MultipartBody.Part,
                                @Part ("title") title:RequestBody,
                                @Part ("imdb_id") imdb_id:RequestBody,
                                @Part ("country") country:RequestBody,
                                @Part ("year") year:RequestBody
    ): Response<UploadMovieModel>


    @POST("api/v1/register/")
    suspend fun registerUser (@Body user: RegisterUserModel): Response<UserRegisteredModel>


    @Multipart
    @POST("oauth/token")
    suspend fun loginUser (@Part ("username") username:RequestBody,
                           @Part ("password") password:RequestBody,
                           @Part("grant_type") grantType:RequestBody
    ):Response<UserAuthModel>

    @GET("api/user")
    suspend fun getUserInfo():Response<UserRegisteredModel>









}