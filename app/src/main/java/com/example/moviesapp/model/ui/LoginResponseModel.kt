package com.example.moviesapp.model.ui

import com.example.moviesapp.model.network.UserAuthModel


sealed class LoginResponseModel {
    data class Success(val data: UserAuthModel) : LoginResponseModel()
    data class Error(val error: String) : LoginResponseModel()
}
