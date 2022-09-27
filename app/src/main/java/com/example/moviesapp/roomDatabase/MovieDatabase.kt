package com.example.moviesapp.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class] , version = 1)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun movieDao() : MovieDao
}





