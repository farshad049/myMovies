package com.example.moviesapp.arch

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.model.mapper.MovieMapper
import com.example.moviesapp.network.ApiClient
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiClient: ApiClient, private val movieMapper: MovieMapper){


    suspend fun getMovieById(movieId: Int): DomainMovieModel? {
        val response = apiClient.getMovieById(movieId)
        if (!response.isSuccessful) {
            return null
        }
        return movieMapper.buildFrom(response.body)
    }



}