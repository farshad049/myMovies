package com.farshad.moviesapp.data.model.network

data class Login(
    val responseBody : UserAuthModel? ,
    val errorMessage : String?
)
