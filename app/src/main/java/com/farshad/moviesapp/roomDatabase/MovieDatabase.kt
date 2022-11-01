package com.farshad.moviesapp.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.farshad.moviesapp.roomDatabase.Dao.FavoriteMovieDao
import com.farshad.moviesapp.roomDatabase.Dao.MovieSearchHistoryDao
import com.farshad.moviesapp.roomDatabase.Entity.FavoriteMovieEntity
import com.farshad.moviesapp.roomDatabase.Entity.SearchHistoryEntity

@Database(entities = [FavoriteMovieEntity::class , SearchHistoryEntity::class] , version = 1)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun favoriteMovieDao() : FavoriteMovieDao
    abstract fun movieSearchHistoryDao() : MovieSearchHistoryDao
}





