package com.farshad.moviesapp.data.remote

import com.farshad.moviesapp.data.model.network.*
import com.farshad.moviesapp.ui.submitMovie.model.UploadMovieModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response


class ApiClient (private val movieService:MovieService){
    suspend fun getMovieById(movieId: Int): SimpleResponse<NetworkMovieModel> {
        return safeApiCall { movieService.getSingleMovie(movieId) }
    }

    suspend fun getMoviesPaging(pageIndex:Int): SimpleResponse<PagingModel> {
        return safeApiCall { movieService.getMoviesPaging(pageIndex) }
    }

    suspend fun getFirstMoviePage(): SimpleResponse<PagingModel> {
        return safeApiCall { movieService.getFirstPageMovie() }
    }

    suspend fun getAllGenres(): SimpleResponse<List<GenresModel>> {
        return safeApiCall { movieService.getAllGenres() }
    }

    suspend fun getFirstPageMovieByGenre(genreId: Int): SimpleResponse<PagingModel> {
        return safeApiCall { movieService.getFirsPageMovieByGenre(genreId) }
    }

    suspend fun getMovieByGenrePaging(genreId: Int, pageIndex:Int): SimpleResponse<PagingModel> {
        return safeApiCall { movieService.getMovieByGenrePaging(genreId,pageIndex) }
    }

    suspend fun getMoviesPagingByName(movieName:String, pageIndex:Int ): SimpleResponse<PagingModel> {
        return safeApiCall { movieService.getMoviesPagingByName(movieName,pageIndex) }
    }

    suspend fun pushMovieBase64(movie: UploadMovieModel): SimpleResponse<UploadMovieModel> {
        return safeApiCall { movieService.pushMovies(movie) }
    }

    suspend fun pushMovieMulti(
        poster: MultipartBody.Part?,
        title: RequestBody,
        imdb_id:RequestBody,
        country:RequestBody,
        year:RequestBody
    ):SimpleResponse<UploadMovieModel>{
        return safeApiCall { movieService.pushMoviesMulti(poster,title,imdb_id,country,year) }
    }

    suspend fun registerUser( user:RegisterPostBody): SimpleResponse<UserRegisteredModel>{
        return  safeApiCall { movieService.registerUser(user) }
    }

    suspend fun loginUser(email:RequestBody,password:RequestBody,grantType:RequestBody):SimpleResponse<UserAuthModel>{
        return safeApiCall { movieService.loginUser(email,password,grantType) }
    }

    suspend fun getUserInfo():SimpleResponse<UserRegisteredModel>{
        return safeApiCall { movieService.getUserInfo() }
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