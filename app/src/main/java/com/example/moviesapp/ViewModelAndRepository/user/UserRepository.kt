package com.example.moviesapp.ViewModelAndRepository.user

import com.example.moviesapp.model.network.RegisterUserModel
import com.example.moviesapp.model.network.UserAuthModel
import com.example.moviesapp.model.network.UserRegisteredModel
import com.example.moviesapp.network.ApiClient
import okhttp3.RequestBody
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiClient: ApiClient) {

    suspend fun registerUser(user: RegisterUserModel):UserRegisteredModel?{
        val response= apiClient.registerUser(user)
        if (!response.isSuccessful){
            return null
        }
        return response.body
    }

    suspend fun loginUser(email: RequestBody, password: RequestBody, grantType: RequestBody):UserAuthModel?{
        val response=apiClient.loginUser(email,password,grantType)
        if (!response.isSuccessful){
            return null
        }
        return response.body

    }

    suspend fun getUserInfo():UserRegisteredModel?{
        val response=apiClient.getUserInfo()
        if (!response.isSuccessful){
            return null
        }
        return response.body
    }
}