package com.farshad.moviesapp.model.ui

import com.farshad.moviesapp.model.network.UserAuthModel


sealed class LoginResponseModel {
    data class Success(val data: UserAuthModel) : LoginResponseModel()
    data class Error(val error: String) : LoginResponseModel()
}
