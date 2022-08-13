package com.example.moviesapp.roomDatabase.entity

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_entity")
data class UserEntity(
    val access_token: String = "",
    val expires_in: Int = 0,
    val refresh_token: String = "",
    val token_type: String = "",
    val isLoggedIn:Boolean= false
):Parcelable

