package com.farshad.moviesapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.farshad.moviesapp.data.db.Dao.FavoriteMovieDao
import com.farshad.moviesapp.data.db.Dao.MovieSearchHistoryDao
import com.farshad.moviesapp.data.db.Entity.FavoriteMovieEntity
import com.farshad.moviesapp.data.db.Entity.SearchHistoryEntity

@Database(entities = [FavoriteMovieEntity::class , SearchHistoryEntity::class] , version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun favoriteMovieDao() : FavoriteMovieDao
    abstract fun movieSearchHistoryDao() : MovieSearchHistoryDao
}





