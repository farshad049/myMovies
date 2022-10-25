package com.example.moviesapp.roomDatabase

import android.content.Context
import androidx.room.Room
import com.example.moviesapp.roomDatabase.Dao.FavoriteMovieDao
import com.example.moviesapp.roomDatabase.Dao.MovieSearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context : Context
    ): MovieDatabase {
      return Room.databaseBuilder(
            context ,
            MovieDatabase::class.java ,
            "movie_database"
             ).build()
    }


    @Singleton
    @Provides
    fun providesFavoriteMovieDao (database: MovieDatabase) : FavoriteMovieDao {
        return database.favoriteMovieDao()
    }

    @Singleton
    @Provides
    fun providesMovieSearchHistoryDao (database: MovieDatabase) : MovieSearchHistoryDao {
        return database.movieSearchHistoryDao()
    }

}