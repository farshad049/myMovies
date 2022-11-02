package com.farshad.moviesapp.model.network

sealed interface RegisterResponseModel {
    data class Success(val data: UserRegisteredModel?) : RegisterResponseModel
    data class Error(val error: String?) : RegisterResponseModel
}