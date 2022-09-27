package com.example.moviesapp.roomDatabase

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val movieDao: MovieDao
) {

    suspend fun insertMovie(movie : MovieEntity){
        movieDao.insert(movie)
    }

    suspend fun deleteMovie(movie : MovieEntity){
        movieDao.delete(movie)
    }

    suspend fun getAllMovies() : Flow<List<MovieEntity>>{
        return movieDao.getAllItemEntities()

    }







}