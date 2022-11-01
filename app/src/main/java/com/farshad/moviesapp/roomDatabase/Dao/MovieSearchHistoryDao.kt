package com.farshad.moviesapp.roomDatabase.Dao

import androidx.room.*
import com.farshad.moviesapp.roomDatabase.Entity.SearchHistoryEntity
import com.farshad.moviesapp.roomDatabase.Entity.SearchHistoryEntityWithoutId
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieSearchHistoryDao {

    @Query("SELECT DISTINCT movieId,movieTitle FROM movie_search_history ORDER BY id DESC")
    fun getAllItemEntities(): Flow<List<SearchHistoryEntityWithoutId>>

    @Insert(onConflict = OnConflictStrategy.REPLACE  )
    suspend fun insert(itemEntity: SearchHistoryEntity)

    @Delete
    suspend fun delete(itemEntity: SearchHistoryEntity)

    @Update
    suspend fun update(itemEntity: SearchHistoryEntity)

    @Query("DELETE FROM movie_search_history")
    suspend fun deleteAll()

    @Query("DELETE FROM movie_search_history WHERE movieId=:id")
    suspend fun deleteMovieById(id:Int)

}