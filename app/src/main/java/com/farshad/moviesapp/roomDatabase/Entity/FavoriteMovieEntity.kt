package com.farshad.moviesapp.roomDatabase.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_movie")
data class FavoriteMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
)

