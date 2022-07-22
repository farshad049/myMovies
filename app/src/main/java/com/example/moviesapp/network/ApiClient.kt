package com.example.moviesapp.network

import com.example.moviesapp.model.network.NetworkMovieModel
import com.example.moviesapp.model.network.PagingModel
import retrofit2.Response


class ApiClient (private val movieService:MovieService){
    suspend fun getMovieById(movieId: Int): SimpleResponse<NetworkMovieModel> {
        return safeApiCall { movieService.getSingleMovie(movieId) }
    }

    suspend fun getCharactersPage(pageIndex:Int): SimpleResponse<PagingModel> {
        return safeApiCall { movieService.getMoviesPage(pageIndex) }
    }

    suspend fun getMovieByGenre(genreId: Int): SimpleResponse<List<NetworkMovieModel>> {
        return safeApiCall { movieService.getMovieByGenre(genreId) }
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