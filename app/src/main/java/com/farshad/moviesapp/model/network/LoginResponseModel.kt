package com.farshad.moviesapp.model.network


sealed interface LoginResponseModel {
    data class Success(val data: UserAuthModel? = null) : LoginResponseModel
    data class Error(val error: String? = null) : LoginResponseModel
    object Loading : LoginResponseModel
}



