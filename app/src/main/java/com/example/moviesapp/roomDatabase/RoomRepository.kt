package com.example.moviesapp.roomDatabase

import com.example.moviesapp.roomDatabase.Dao.FavoriteMovieDao
import com.example.moviesapp.roomDatabase.Dao.MovieSearchHistoryDao
import com.example.moviesapp.roomDatabase.Entity.FavoriteMovieEntity
import com.example.moviesapp.roomDatabase.Entity.SearchHistoryEntity
import com.example.moviesapp.roomDatabase.Entity.SearchHistoryEntityWithoutId
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao ,
    private val movieSearchHistoryDao: MovieSearchHistoryDao
) {

    suspend fun insertFavoriteMovie(movie : FavoriteMovieEntity){
        favoriteMovieDao.insert(movie)
    }

    suspend fun deleteFavoriteMovie(movie : FavoriteMovieEntity){
        favoriteMovieDao.delete(movie)
    }

    suspend fun getAllFavoriteMovies() : Flow<List<FavoriteMovieEntity>>{
        return favoriteMovieDao.getAllItemEntities()
    }





    suspend fun insertMovieSearchHistory(movie : SearchHistoryEntity){
        movieSearchHistoryDao.insert(movie)
    }

    suspend fun deleteMovieSearchHistory(movie : SearchHistoryEntity){
        movieSearchHistoryDao.delete(movie)
    }

    suspend fun deleteMovieSearchById(movieId : Int){
        movieSearchHistoryDao.deleteMovieById(movieId)
    }

    suspend fun getAllMovieSearchHistory() : Flow<List<SearchHistoryEntityWithoutId>>{
        return movieSearchHistoryDao.getAllItemEntities()
    }







}