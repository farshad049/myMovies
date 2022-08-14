package com.example.moviesapp.ViewModelAndRepository

import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.model.mapper.MovieMapper
import com.example.moviesapp.model.network.UploadMovieModel
import com.example.moviesapp.network.ApiClient
import com.example.moviesapp.util.MovieCache
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper,
    ){


    suspend fun getMovieById(movieId: Int): DomainMovieModel? {
        val cachedMovie= MovieCache.movieMap[movieId]
        if (cachedMovie != null){
            return cachedMovie
        }

        val response = apiClient.getMovieById(movieId)
        if (!response.isSuccessful) {
            return null
        }
        val movie =movieMapper.buildFrom(response.body)

        MovieCache.movieMap[movieId]=movie

        return movie
    }


    suspend fun getMovieByGenre(genreId: Int): List<DomainMovieModel?> {
        val cachedSimilarMovie= MovieCache.similarMovieMap[genreId]
        if (cachedSimilarMovie != null){
            return cachedSimilarMovie
        }

        val response = apiClient.getMovieByGenre(genreId)
        if (!response.isSuccessful) {
            return emptyList()
        }
        val similarMovieList=response.body.data.map { movieMapper.buildFrom(it) }

        MovieCache.similarMovieMap[genreId]=similarMovieList

        return similarMovieList
    }


    suspend fun pushMovie(movie:UploadMovieModel):UploadMovieModel?{
        val response= apiClient.pushMovie(movie)
        if (!response.isSuccessful){
            return null
        }
        return response.body
    }












}