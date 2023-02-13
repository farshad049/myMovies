package com.farshad.moviesapp.data.repository

import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.data.model.mapper.MovieMapper
import com.farshad.moviesapp.data.model.network.GenresModel
import com.farshad.moviesapp.data.remote.ApiClient
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper,
){


    suspend fun getFirstPageMovie():List<DomainMovieModel>{
        val response= apiClient.getFirstMoviePage()
        if (!response.isSuccessful){
            return emptyList()
        }
        return response.body.data.map { movieMapper.buildFrom(it) }
    }



    suspend fun getAllGenres():List<GenresModel>{
        val response= apiClient.getAllGenres()
        if (!response.isSuccessful){
            return emptyList()
        }
        return response.body
    }







}