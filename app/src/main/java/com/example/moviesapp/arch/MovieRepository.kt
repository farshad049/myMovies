package com.example.moviesapp.arch

import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.model.mapper.MovieMapper
import com.example.moviesapp.model.network.RegisterUserModel
import com.example.moviesapp.model.network.UploadMovieModel
import com.example.moviesapp.network.ApiClient
import okhttp3.RequestBody
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiClient: ApiClient, private val movieMapper: MovieMapper){


    suspend fun getMovieById(movieId: Int): DomainMovieModel? {
        val response = apiClient.getMovieById(movieId)
        if (!response.isSuccessful) {
            return null
        }
        return movieMapper.buildFrom(response.body)
    }

    suspend fun getMovieByGenre(genreId: Int): List<DomainMovieModel?> {
        val response = apiClient.getMovieByGenre(genreId)
        if (!response.isSuccessful) {
            return emptyList()
        }
        return response.body.data.map { movieMapper.buildFrom(it) }
    }

    suspend fun pushMovie(movie:UploadMovieModel):UploadMovieModel?{
        val response= apiClient.pushMovie(movie)
        if (!response.isSuccessful){
            return null
        }
        return response.body
    }

    suspend fun registerUser(user:RegisterUserModel):Any?{
        val response= apiClient.registerUser(user)
        if (!response.isSuccessful){
            return null
        }
        return response.body
    }

    suspend fun loginUser(email: RequestBody, password: RequestBody, grantType: RequestBody):Any?{
        val response=apiClient.loginUser(email,password,grantType)
        if (!response.isSuccessful){
            return null
        }
        return response.body
    }



}