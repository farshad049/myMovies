package com.farshad.moviesapp.ui.register.model

import com.farshad.moviesapp.data.model.network.UserRegisteredModel

sealed interface RegisterResponseModel {
    data class Success(val data: UserRegisteredModel?) : RegisterResponseModel
    data class Error(val error: String?) : RegisterResponseModel
}