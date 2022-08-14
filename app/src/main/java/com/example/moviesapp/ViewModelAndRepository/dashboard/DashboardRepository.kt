package com.example.moviesapp.ViewModelAndRepository.dashboard

import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.model.mapper.MovieMapper
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
}