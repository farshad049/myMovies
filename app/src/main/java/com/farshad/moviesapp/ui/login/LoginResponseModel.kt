package com.farshad.moviesapp.ui.login

import com.farshad.moviesapp.data.model.network.UserAuthModel


sealed interface LoginResponseModel {
    data class Success(val data: UserAuthModel) : LoginResponseModel
    data class Error(val error: String) : LoginResponseModel
    object Loading : LoginResponseModel
}



