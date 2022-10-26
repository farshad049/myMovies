package com.example.moviesapp.model.ui

import com.example.moviesapp.model.network.UserAuthModel

sealed interface LoginResponseModel1 {

        data class Success(
                val userCredential : UserAuthModel,
        ):LoginResponseModel1

        object Loading: LoginResponseModel1
}

