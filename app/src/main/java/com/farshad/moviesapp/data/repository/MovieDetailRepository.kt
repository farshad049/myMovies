package com.farshad.moviesapp.data.repository

import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.data.model.mapper.MovieMapper
import com.farshad.moviesapp.data.remote.ApiClient
import com.farshad.moviesapp.util.MovieCache
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(
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


    suspend fun getMovieByGenre(genreId: Int): List<DomainMovieModel> {
        val cachedSimilarMovie= MovieCache.similarMovieMap[genreId]
        if (cachedSimilarMovie != null){
            return cachedSimilarMovie
        }

        val response = apiClient.getFirstPageMovieByGenre(genreId)
        if (!response.isSuccessful) {
            return emptyList()
        }
        val similarMovieList=response.body.data.map { movieMapper.buildFrom(it) }

        MovieCache.similarMovieMap[genreId]=similarMovieList

        return similarMovieList
    }
}