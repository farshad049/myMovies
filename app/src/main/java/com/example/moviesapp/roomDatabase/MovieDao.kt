package com.example.moviesapp.roomDatabase

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getAllItemEntities(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE  )
    suspend fun insert(itemEntity: MovieEntity)

    @Delete
    suspend fun delete(itemEntity: MovieEntity)

    @Update
    suspend fun update(itemEntity: MovieEntity)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()

}