package com.example.moviesapp.network

import com.example.moviesapp.model.network.NetworkMovieModel
import com.example.moviesapp.model.network.PagingModel
import com.example.moviesapp.model.network.RegisterUserModel
import com.example.moviesapp.model.network.UploadMovieModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response


class ApiClient (private val movieService:MovieService){
    suspend fun getMovieById(movieId: Int): SimpleResponse<NetworkMovieModel> {
        return safeApiCall { movieService.getSingleMovie(movieId) }
    }

    suspend fun getCharactersPage(pageIndex:Int): SimpleResponse<PagingModel> {
        return safeApiCall { movieService.getMoviesPage(pageIndex) }
    }

    suspend fun getMovieByGenre(genreId: Int): SimpleResponse<PagingModel> {
        return safeApiCall { movieService.getMovieByGenre(genreId) }
    }

    suspend fun getMoviesPageByName(movieName:String ,pageIndex:Int ): SimpleResponse<PagingModel> {
        return safeApiCall { movieService.getMoviesPageByName(movieName,pageIndex) }
    }

    suspend fun pushMovie(movie:UploadMovieModel): SimpleResponse<UploadMovieModel> {
        return safeApiCall { movieService.pushMovies(movie) }
    }

    suspend fun pushMovieMulti(poster: MultipartBody.Part, title: RequestBody, imdb_id:RequestBody, country:RequestBody, year:RequestBody):SimpleResponse<UploadMovieModel>{
        return safeApiCall { movieService.pushMoviesMulti(poster,title,imdb_id,country,year) }
    }

    suspend fun registerUser( user:RegisterUserModel): SimpleResponse<Any>{
        return  safeApiCall { movieService.registerUser(user) }
    }


    //run safe check for network issues
    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}