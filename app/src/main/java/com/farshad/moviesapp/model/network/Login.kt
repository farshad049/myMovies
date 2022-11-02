package com.farshad.moviesapp.model.network

data class Login(
    val responseBody : UserAuthModel? ,
    val errorMessage : String?
)
