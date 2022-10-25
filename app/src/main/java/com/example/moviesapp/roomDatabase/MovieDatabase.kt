package com.example.moviesapp.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesapp.roomDatabase.Dao.FavoriteMovieDao
import com.example.moviesapp.roomDatabase.Dao.MovieSearchHistoryDao
import com.example.moviesapp.roomDatabase.Entity.FavoriteMovieEntity
import com.example.moviesapp.roomDatabase.Entity.SearchHistoryEntity

@Database(entities = [FavoriteMovieEntity::class , SearchHistoryEntity::class] , version = 1)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun favoriteMovieDao() : FavoriteMovieDao
    abstract fun movieSearchHistoryDao() : MovieSearchHistoryDao
}





