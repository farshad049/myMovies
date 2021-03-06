package com.example.moviesapp.arch

import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.model.mapper.MovieMapper
import com.example.moviesapp.network.ApiClient
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



}