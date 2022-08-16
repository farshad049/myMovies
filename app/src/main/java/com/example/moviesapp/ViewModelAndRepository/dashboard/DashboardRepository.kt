package com.example.moviesapp.ViewModelAndRepository.dashboard

import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.model.mapper.MovieMapper
import com.example.moviesapp.model.network.GenresModel
import com.example.moviesapp.network.ApiClient
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper,
){


    suspend fun getFirstPageMovie():List<DomainMovieModel?>{
        val response= apiClient.getFirstMoviePage()
        if (!response.isSuccessful){
            return emptyList()
        }
        return response.body.data.map { movieMapper.buildFrom(it) }
    }


    suspend fun getMovieByGenre(genreId: Int): List<DomainMovieModel?> {

        val response = apiClient.getFirstPageMovieByGenre(genreId)
        if (!response.isSuccessful) {
            return emptyList()
        }
        val similarMovieList=response.body.data.map { movieMapper.buildFrom(it) }


        return similarMovieList
    }


    suspend fun getAllGenres():List<GenresModel>{
        val response= apiClient.getAllGenres()
        if (!response.isSuccessful){
            return emptyList()
        }
        return response.body
    }







}