package com.example.moviesapp.roomDatabase

import androidx.room.*
import com.example.moviesapp.roomDatabase.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_entity")
    fun getAllItemEntities(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE  )
    suspend fun insert(itemEntity: UserEntity)

    @Delete
    suspend fun delete(itemEntity: UserEntity)

    @Update
    suspend fun update(itemEntity: UserEntity)

    @Query("DELETE FROM user_entity")
    suspend fun deleteAll()

}